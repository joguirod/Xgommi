document.addEventListener('DOMContentLoaded', function() {
    // Função para login
    const loginBtn = document.getElementById('loginBtn');
    if (loginBtn) {
        loginBtn.addEventListener('click', async function() {
            const login = document.getElementById('inputLogin').value.trim();
            const password = document.getElementById('inputPassword3').value.trim();

            if (!login || !password) {
                alert('Todos os campos são obrigatórios.');
                return;
            }

            const loginData = {
                login: login,
                password: password
            };

            try {
                const response = await fetch('http://localhost:8080/api/xgommi/gommiuser/login', { // Ajuste a URL conforme necessário
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(loginData)
                });

                if (response.ok) {
                    const responseData = await response.json();
                    localStorage.setItem('userId', responseData.idGommiUser); // Armazena o ID do usuário no localStorage
                    window.location.href = 'index.html'; // Redireciona para a página principal
                } else {
                    alert('Erro ao fazer login.');
                }
            } catch (error) {
                alert('Erro na requisição. Tente novamente.');
                console.error('Erro ao fazer login:', error);
            }
        });
    }

    // Função para cadastro
    const registerBtn = document.getElementById('registerBtn');
    if (registerBtn) {
        registerBtn.addEventListener('click', async function() {
            const login = document.getElementById('inputRegisterLogin').value.trim();
            const password = document.getElementById('inputRegisterPassword').value.trim();
            const email = document.getElementById('inputRegisterEmail').value.trim();
            const name = document.getElementById('inputRegisterName').value.trim();

            if (!login || !password || !email || !name) {
                alert('Todos os campos são obrigatórios.');
                return;
            }

            const userData = {
                login: login,
                password: password,
                email: email,
                name: name
            };

            try {
                const response = await fetch('http://localhost:8080/api/xgommi/gommiuser', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(userData)
                });

                if (response.ok) {
                    alert('Cadastro realizado com sucesso!');
                    window.location.href = 'login.html'; // Redireciona para a tela de login após o cadastro bem-sucedido
                } else {
                    alert('Erro ao realizar cadastro.');
                }
            } catch (error) {
                alert('Erro na requisição. Tente novamente.');
                console.error('Erro ao realizar cadastro:', error);
            }
        });
    }
});
