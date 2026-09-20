<script setup>
import { ref, onMounted } from 'vue';
import authService from './services/authService';

// Estados reactivos
const shelters = ref([]);
const username = ref('');
const password = ref('');
const loggedIn = ref(authService.isLoggedIn());
const errorMessage = ref('');

// Campos para el formulario (Crear / Editar)
const editingId = ref(null); // Si tiene valor, estamos editando
const newName = ref('');
const newType = ref('SHELTER');
const newRegion = ref('');
const newElevation = ref('');
const newLatitude = ref('');
const newLongitude = ref('');
const successMessage = ref('');

// Cargar refugios (GET público)
const fetchShelters = async () => {
  try {
    const response = await fetch('http://localhost:8080/api/v1/shelters');
    shelters.value = await response.json();
  } catch (error) {
    console.error('Error al cargar refugios:', error);
  }
};

// Iniciar sesión
const handleLogin = async () => {
  try {
    errorMessage.value = '';
    await authService.login(username.value, password.value);
    loggedIn.value = true;
    username.value = '';
    password.value = '';
  } catch (error) {
    errorMessage.value = 'Usuario o contraseña incorrectos';
  }
};

// Cerrar sesión
const handleLogout = () => {
  authService.logout();
  loggedIn.value = false;
  cancelEdit();
};

// Guardar o Actualizar Refugio (POST o PUT)
const handleSubmitShelter = async () => {
  try {
    successMessage.value = '';
    const token = authService.getToken();
    const url = editingId.value 
      ? `http://localhost:8080/api/v1/shelters/${editingId.value}`
      : 'http://localhost:8080/api/v1/shelters';
    
    const method = editingId.value ? 'PUT' : 'POST';

    const response = await fetch(url, {
      method: method,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({
        name: newName.value,
        type: newType.value,
        region: newRegion.value,
        elevation: Number(newElevation.value),
        latitude: Number(newLatitude.value),
        longitude: Number(newLongitude.value)
      }),
    });

    if (!response.ok) {
      throw new Error('Error al procesar la operación (¿falta de permisos?)');
    }

    successMessage.value = editingId.value ? '¡Registro actualizado con éxito!' : '¡Refugio/Pico creado con éxito!';
    
    cancelEdit();
    fetchShelters();
  } catch (error) {
    alert(error.message);
  }
};

// Preparar formulario para Editar
const startEdit = (shelter) => {
  editingId.value = shelter.id;
  newName.value = shelter.name;
  newType.value = shelter.type;
  newRegion.value = shelter.region;
  newElevation.value = shelter.elevation;
  newLatitude.value = shelter.latitude;
  newLongitude.value = shelter.longitude;
};

// Cancelar edición
const cancelEdit = () => {
  editingId.value = null;
  newName.value = '';
  newType.value = 'SHELTER';
  newRegion.value = '';
  newElevation.value = '';
  newLatitude.value = '';
  newLongitude.value = '';
};

// Eliminar refugio (DELETE protegido con Token JWT)
const handleDelete = async (id) => {
  if (!confirm('¿Estás seguro de que deseas eliminar este registro?')) return;

  try {
    const token = authService.getToken();
    const response = await fetch(`http://localhost:8080/api/v1/shelters/${id}`, {
      method: 'DELETE',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });

    if (!response.ok) {
      throw new Error('Error al eliminar el registro');
    }

    fetchShelters();
  } catch (error) {
    alert(error.message);
  }
};

onMounted(() => {
  fetchShelters();
});
</script>

<template>
  <div style="font-family: Arial, sans-serif; max-width: 900px; margin: 0 auto; padding: 20px;">
    <h1>Pyrenean Safe 🏔️</h1>

    <!-- Sección de Autenticación -->
    <div style="background: #f4f4f4; padding: 15px; margin-bottom: 20px; border-radius: 8px;">
      <div v-if="!loggedIn">
        <h3>Iniciar Sesión (Admin)</h3>
        <form @submit.prevent="handleLogin">
          <input v-model="username" placeholder="Usuario (ej. admin)" required style="margin-right: 10px; padding: 5px;" />
          <input v-model="password" type="password" placeholder="Contraseña" required style="margin-right: 10px; padding: 5px;" />
          <button type="submit" style="padding: 5px 10px;">Entrar</button>
        </form>
        <p v-if="errorMessage" style="color: red;">{{ errorMessage }}</p>
      </div>
      <div v-else>
        <p>🟢 <strong>Sesión iniciada como Administrador</strong></p>
        <button @click="handleLogout" style="padding: 5px 10px; background: #ff4d4d; color: white; border: none; border-radius: 4px;">Cerrar Sesión</button>
        
        <!-- Formulario protegido para Crear o Editar -->
        <div style="margin-top: 15px; background: #e2e8f0; padding: 15px; border-radius: 6px;">
          <h4>{{ editingId ? 'Editar Registro (ID: ' + editingId + ')' : 'Añadir Nuevo Refugio o Pico (Solo Admin)' }}</h4>
          <form @submit.prevent="handleSubmitShelter">
            <input v-model="newName" placeholder="Nombre" required style="margin: 5px; padding: 5px;" />
            <select v-model="newType" style="margin: 5px; padding: 5px;">
              <option value="SHELTER">Refugio</option>
              <option value="PEAK">Pico</option>
            </select>
            <input v-model="newRegion" placeholder="Región" required style="margin: 5px; padding: 5px;" />
            <input v-model="newElevation" type="number" placeholder="Elevación (m)" required style="margin: 5px; padding: 5px;" />
            <input v-model="newLatitude" type="number" step="any" placeholder="Latitud" required style="margin: 5px; padding: 5px;" />
            <input v-model="newLongitude" type="number" step="any" placeholder="Longitud" required style="margin: 5px; padding: 5px;" />
            <br>
            <button type="submit" style="padding: 6px 12px; background: #2b6cb0; color: white; border: none; border-radius: 4px; margin-top: 5px;">
              {{ editingId ? 'Actualizar Registro' : 'Guardar Registro' }}
            </button>
            <button v-if="editingId" @click="cancelEdit" type="button" style="padding: 6px 12px; background: #718096; color: white; border: none; border-radius: 4px; margin-top: 5px; margin-left: 5px;">
              Cancelar
            </button>
          </form>
          <p v-if="successMessage" style="color: green; font-weight: bold; margin-top: 5px;">{{ successMessage }}</p>
        </div>
      </div>
    </div>

    <!-- Listado de Refugios -->
    <h2>Refugios y Picos Disponibles</h2>
    <ul style="list-style-type: none; padding: 0;">
      <li v-for="shelter in shelters" :key="shelter.id" style="background: #fff; border: 1px solid #cbd5e0; padding: 10px; margin-bottom: 8px; border-radius: 6px; display: flex; justify-content: space-between; align-items: center;">
        <div>
          <strong>{{ shelter.name }}</strong> ({{ shelter.type }}) - {{ shelter.region }} [{{ shelter.elevation }}m]
        </div>
        <!-- Botones de administración (solo visibles si está logueado) -->
        <div v-if="loggedIn">
          <button @click="startEdit(shelter)" style="background: #d69e2e; color: white; border: none; padding: 4px 8px; border-radius: 4px; margin-right: 5px; cursor: pointer;">Editar</button>
          <button @click="handleDelete(shelter.id)" style="background: #e53e3e; color: white; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer;">Eliminar</button>
        </div>
      </li>
    </ul>
  </div>
</template>