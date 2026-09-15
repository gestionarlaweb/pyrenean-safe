// src/services/shelterService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/shelters';

export default {
    // Obtener todos o filtrar por región y/o tipo
    getShelters(params = {}) {
        return axios.get(API_URL, { params });
    },

    // Crear un nuevo refugio o pico
    createShelter(shelterData) {
        return axios.post(API_URL, shelterData);
    },

    // Actualizar un registro existente
    updateShelter(id, shelterData) {
        return axios.put(`${API_URL}/${id}`, shelterData);
    },

    // Eliminar un registro
    deleteShelter(id) {
        return axios.delete(`${API_URL}/${id}`);
    }
};