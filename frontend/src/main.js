document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM completamente carregado e processado.');

    // Função para buscar postagens
    async function fetchPosts() {
        try {
            console.log('Iniciando fetch das postagens...');

            const response = await fetch('http://localhost:8080/api/xgommi/post');
            console.log('Resposta da API:', response);

            if (!response.ok) {
                throw new Error('Erro ao buscar postagens. Status:', response.status);
            }

            const posts = await response.json();
            console.log('Postagens recebidas:', posts);

            const postContainer = document.querySelector('.post-container');
            if (!postContainer) {
                console.error('Contêiner de postagens não encontrado');
                return;
            }

            postContainer.innerHTML = ''; // Limpa o container

            posts.forEach(post => {
                const postElement = document.createElement('div');
                postElement.classList.add('bg-gray-200', 'text-black', 'p-4', 'rounded-md', 'mb-5');
                postElement.innerHTML = `<p>${post.text}</p><p>Autor: ${post.author.name}</p>`;
                postContainer.appendChild(postElement);
            });
        } catch (error) {
            console.error('Erro ao buscar postagens:', error);
        }
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
                alert('Postagem criada com sucesso!');
                fetchPosts(); // Atualiza a lista de postagens
            } else {
                alert('Erro ao criar postagem. Status:', response.status);
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
