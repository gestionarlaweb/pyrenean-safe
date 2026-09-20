const API_URL = 'http://localhost:8080/api/v1/shelters';

export const shelterService = {
    async getAll() {
        const res = await fetch(API_URL);
        if (!res.ok) throw new Error('Error al cargar los refugios');
        return res.json();
    },

    async create(shelterData, token) {
        const res = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(shelterData)
        });
        if (!res.ok) throw new Error('Error al crear el registro');
        return res.json();
    },

    async update(id, shelterData, token) {
        const res = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(shelterData)
        });
        if (!res.ok) throw new Error('Error al actualizar el registro');
        return res.json();
    },

    async delete(id, token) {
        const res = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (!res.ok) throw new Error('Error al eliminar el registro');
        return true;
    }
};