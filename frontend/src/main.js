document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM completamente carregado e processado.');

    // Função para buscar postagens
    async function fetchPosts() {
        try {
            console.log('Iniciando fetch das postagens...');

            const response = await fetch('http://localhost:8080/api/xgommi/post');
            console.log('Resposta da API:', response);

            if (!response.ok) {
                throw new Error('Erro ao buscar postagens. Status: ' + response.status);
            }

            const posts = await response.json();
            console.log('Postagens recebidas:', posts);

            const postContainer = document.getElementById('posts-container');
            if (!postContainer) {
                console.error('Contêiner de postagens não encontrado');
                return;
            }

            postContainer.innerHTML = ''; // Limpa o container

            posts.forEach(post => {
                addPostToContainer(post); // Adiciona postagens ao contêiner
            });
        } catch (error) {
            console.error('Erro ao buscar postagens:', error);
        }
    }

    // Função para adicionar uma nova postagem ao contêiner com a estética desejada
    function addPostToContainer(post) {
        const postContainer = document.getElementById('posts-container');

        // Criação do contêiner da postagem
        const postWrapper = document.createElement('div');
        postWrapper.classList.add('flex', 'flex-col', 'mb-5', 'w-full', 'items-center');

        // Adiciona o autor
        const authorDiv = document.createElement('div');
        authorDiv.classList.add('flex', 'mr-96', 'justify-center');
        authorDiv.textContent = `@${post.author.name} publicou:`;
        postWrapper.appendChild(authorDiv);

        // Adiciona o conteúdo da postagem
        const contentDiv = document.createElement('div');
        contentDiv.classList.add('flex', 'justify-center');
        const contentBox = document.createElement('div');
        contentBox.classList.add('bg-gray-200', 'text-black', 'p-4', 'md:w-2/3', 'lg:w-1/2', 'h-auto', 'rounded-md', 'mb-5');
        contentBox.innerHTML = `<p>${post.text}</p>`; // Adiciona o texto da postagem
        contentDiv.appendChild(contentBox);
        postWrapper.appendChild(contentDiv);

        // Adiciona os botões de "Gostei" e "Não Gostei"
        const reactionDiv = document.createElement('div');
        reactionDiv.classList.add('flex', 'justify-center', 'ml-80', '-mt-10');
        reactionDiv.innerHTML = `Não Gostei: 
            <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button type="button" class="justify-center m-2 flex w-8 bg-red-600">
                    <img src="deslike.ico" alt="Deslike" class="w-4 h-5">
                </button> 
                Gostei:
                <button type="button" class="justify-center m-2 flex w-8 bg-green-600">
                    <img src="like.ico" alt="Curtir" class="w-4 h-5">
                </button>
            </div>`;
        postWrapper.appendChild(reactionDiv);

        // Adiciona o contêiner da postagem ao contêiner principal
        postContainer.appendChild(postWrapper);
    }

    // Função para adicionar uma nova postagem
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

    // Verifica se o botão de salvar postagem existe
    const savePostBtn = document.getElementById('savePostBtn');
    if (savePostBtn) {
        console.log('Botão de salvar postagem encontrado.');
        savePostBtn.addEventListener('click', addPost);
    } else {
        console.error('Botão de salvar postagem não encontrado.');
    }

    // Carrega postagens ao iniciar
    fetchPosts();
});
