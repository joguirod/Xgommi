const apiLoginUrl = 'http://localhost:8080/login'; //não ta certo

// Função para realizar o login
async function loginUser(loginData) {
  try {
    const response = await fetch(`${apiLoginUrl}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(loginData),
    });

    if (response.ok) {
      const data = await response.json();
      console.log('Login bem-sucedido:', data);
      // Armazena o token ou as informações do usuário no localStorage para ser usado em novas requisições quando autenticadas
      localStorage.setItem('userToken', data.token); // Olhar oq a API retorna
      alert('Login bem-sucedido!');
      window.location.href = 'index.html'; // Após o login o caba vai para a tela inicial "MAIN"
    } else {
      console.error('Erro no login:', response.statusText);
      alert('Falha no login. Verifique suas credenciais.');
    }
  } catch (error) {
    console.error('Erro ao tentar logar:', error);
    alert('Erro ao tentar logar. Tente novamente.');
  }
}

// Listener para o botão de login
document.querySelector('button').addEventListener('click', function(event) {
  event.preventDefault(); 

  const login = document.getElementById('inputLogin').value;
  const password = document.getElementById('inputPassword3').value; //Sem mãe que coloca o ID como WORD3

  if (login.trim() === "" || password.trim() === "") { // Se o cara colocar um espaço sem querer no fim do login ou senha, ele considera esse espaço como parte da senha. Isso evita esse problema
    alert('Login e senha são obrigatórios.');
    return;
  }

  const loginData = {
    login: login,
    password: password
  };

  loginUser(loginData);
});
