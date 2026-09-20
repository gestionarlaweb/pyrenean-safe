const API_URL = 'http://localhost:8080/api/v1/auth';

export default {
    async login(username, password) {
        const response = await fetch(`${API_URL}/authenticate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            throw new Error('Credenciales inválidas o error en el servidor');
        }

        const data = await response.json();
        if (data.token) {
            localStorage.setItem('token', data.token);
        }
        return data;
    },

    logout() {
        localStorage.removeItem('token');
    },

    getToken() {
        return localStorage.getItem('token');
    },

    isLoggedIn() {
        return !!localStorage.getItem('token');
    }
};