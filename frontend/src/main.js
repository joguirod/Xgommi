document.addEventListener('DOMContentLoaded', main);

function main() {
    setupEventListeners();
    fetchPosts();
}

function limparPosts() {
    const postContainer = document.getElementById('posts-container');
    postContainer.innerHTML = '';
}

async function fetchPosts(login) {
    try {
        console.log('Iniciando fetch das postagens...');

        const url = login 
        ? `http://localhost:8080/api/xgommi/post/author/login/${login}`
        : 'http://localhost:8080/api/xgommi/post';

        const response = await fetch(url);
        console.log('Resposta da API:', response);

        if (!response.ok) {
            throw new Error('Erro ao buscar postagens. Status: ' + response.status);
        }

        const posts = await response.json();
        console.log('Postagens recebidas:', posts);

        posts.reverse(); // Inverte a ordem das postagens para mostrar as mais recentes primeiro

        const postContainer = document.getElementById('posts-container');
        if (!postContainer) {
            console.error('Contêiner de postagens não encontrado');
            return;
        }

        limparPosts(); // Limpa o container

        posts.forEach(post => {
            addPostToContainer(post); // Adiciona postagens ao contêiner
        });
    } catch (error) {
        console.error('Erro ao buscar postagens:', error);
    }
}

function addPostToContainer(post) {
    const postContainer = document.getElementById('posts-container');

    const postWrapper = document.createElement('div');
    postWrapper.classList.add('flex', 'flex-col', 'mb-5', 'w-full', 'items-center');
    postWrapper.setAttribute('data-post-id', post.idPost);
    postWrapper.setAttribute('data-author-id', post.author.idGommiUser);

    const authorDiv = document.createElement('div');
    authorDiv.classList.add('flex', 'mr-96', 'justify-center');
    authorDiv.textContent = `@${post.author.login} publicou:`;
    postWrapper.appendChild(authorDiv);

    const contentDiv = document.createElement('div');
    contentDiv.classList.add('flex', 'justify-center');
    const contentBox = document.createElement('div');
    contentBox.classList.add('bg-gray-200', 'text-black', 'p-4', 'md:w-2/3', 'lg:w-1/2', 'h-auto', 'rounded-md', 'mb-5');
    contentBox.innerHTML = `<p>${post.text}</p>`;
    contentDiv.appendChild(contentBox);
    postWrapper.appendChild(contentDiv);

    const reactionDiv = document.createElement('div');
    reactionDiv.classList.add('flex', 'justify-center', 'ml-80', '-mt-10');
    reactionDiv.innerHTML = `
        Não Gostei: 
        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
            <button type="button" class="justify-center m-2 flex w-8 bg-red-600">
                <img src="deslike.ico" alt="Deslike" class="w-4 h-5">
            </button> 
            Gostei:
            <button type="button" class="justify-center m-2 flex w-8 bg-green-600">
                <img src="like.ico" alt="Curtir" class="w-4 h-5">
            </button>
        </div>`;

    const usuarioLogadoId = localStorage.getItem('userId');
    console.log('ID do usuário logado:', usuarioLogadoId);
    console.log('ID do autor da postagem:', post.author.idGommiUser);

    if (Number(usuarioLogadoId) === post.author.idGommiUser) {
        reactionDiv.innerHTML += `
    <button type="button" class="block bg-gray-500 text-white px-4 py-2 rounded" onclick="confirmarDelecao(${post.idPost})">
        Excluir
    </button>
    `;
    }

    postWrapper.appendChild(reactionDiv);
    postContainer.appendChild(postWrapper);
}

async function addPost() {
    const postContent = document.getElementById('postContent').value;
    console.log('Conteúdo da postagem:', postContent);

    if (postContent.trim() === "") {
        alert('O conteúdo da postagem não pode estar vazio.');
        return;
    }

    const postData = {
        text: postContent,
        gommiUserId: localStorage.getItem('userId')  // Obtém o ID do usuário
    };

    console.log('Dados da postagem a serem enviados:', postData);

    if (!postData.gommiUserId) {
        console.error('ID do usuário não encontrado no localStorage.');
        alert('Erro: ID do usuário não encontrado.');
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/xgommi/post', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(postData)
        });

        console.log('Resposta ao criar postagem:', response);

        if (response.ok) {
            const newPost = await response.json(); // Pega a nova postagem retornada pela API
            addPostToContainer(newPost); // Adiciona a nova postagem ao contêiner
            alert('Postagem criada com sucesso!');
            document.getElementById('postContent').value = ''; // Limpa o campo após enviar
        } else {
            alert('Erro ao criar postagem. Status: ' + response.status);
        }
    } catch (error) {
        alert('Erro na requisição. Tente novamente.');
        console.error('Erro ao criar postagem:', error);
    }
}

function realizarPesquisa() {
    let loginPesquisa = document.getElementById('loginPesquisa');

    if (loginPesquisa === null) {
        return;
    }
    loginPesquisa = loginPesquisa.value.trim();
    limparPosts();

    if (loginPesquisa === '') {
        fetchPosts();
    } else {
        fetchPosts(loginPesquisa);
    }
}

async function confirmarDelecao(postId) {
    console.log('ID da postagem para exclusão:', postId); // Verifica se o ID está correto
    const usuarioLogadoId = localStorage.getItem('userId');
    
    // Obtém o elemento da postagem
    const postElement = document.querySelector(`div[data-post-id='${postId}']`);
    const autorId = postElement ? postElement.getAttribute('data-author-id') : null;

    console.log('ID do autor da postagem:', autorId); // Verifica se o ID do autor está correto

    if (usuarioLogadoId === autorId) {
        const confirmar = confirm('Você tem certeza de que deseja excluir esta postagem?');
        if (confirmar) {
            await deletarPost(postId);
        }
    } else {
        alert('Você não tem permissão para excluir esta postagem.');
    }
}

async function deletarPost(postId) {
    try {
        const response = await fetch(`http://localhost:8080/api/xgommi/post/${postId}`, {
            method: 'DELETE'
        });

        console.log('Resposta ao deletar postagem:', response);

        if (response.ok) {
            // Remove a postagem do contêiner
            const postContainer = document.getElementById('posts-container');
            const postToRemove = document.querySelector(`div[data-post-id='${postId}']`);
            if (postToRemove) {
                postContainer.removeChild(postToRemove);
                alert('Postagem deletada com sucesso!');
            }
        } else {
            alert('Erro ao deletar postagem. Status: ' + response.status);
        }
    } catch (error) {
        alert('Erro na requisição. Tente novamente.');
        console.error('Erro ao deletar postagem:', error);
    }
}

function setupEventListeners() {
    // Verifica se o botão de salvar postagem existe
    const savePostBtn = document.getElementById('savePostBtn');
    if (savePostBtn) {
        console.log('Botão de salvar postagem encontrado.');
        savePostBtn.addEventListener('click', addPost);
    } else {
        console.error('Botão de salvar postagem não encontrado.');
    }

    // Verifica se o botão de pesquisa existe
    const searchButton = document.querySelector('button[type="button"]');
}