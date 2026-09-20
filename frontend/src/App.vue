<script setup>
import { ref, onMounted } from 'vue';
import { shelterService } from './services/shelterService';
import authService from './services/authService';
import LoginModal from './components/LoginModal.vue';
import ShelterForm from './components/ShelterForm.vue';
import ShelterList from './components/ShelterList.vue';

const shelters = ref([]);
const isLoggedIn = ref(authService.isLoggedIn());
const shelterToEdit = ref(null);
const feedback = ref({ message: '', type: 'success' });

const loadShelters = async () => {
  try {
    shelters.value = await shelterService.getAll();
  } catch (err) {
    showFeedback(err.message, 'error');
  }
};

const handleSaveShelter = async (formData) => {
  try {
    const token = authService.getToken();
    if (shelterToEdit.value) {
      await shelterService.update(shelterToEdit.value.id, formData, token);
      showFeedback('¡Registro actualizado correctamente!');
    } else {
      await shelterService.create(formData, token);
      showFeedback('¡Nuevo refugio/pico creado con éxito!');
    }
    shelterToEdit.value = null;
    loadShelters();
  } catch (err) {
    showFeedback(err.message, 'error');
  }
};

const handleDeleteShelter = async (id) => {
  if (!confirm('¿Estás seguro de eliminar este registro permanentemente?')) return;
  try {
    const token = authService.getToken();
    await shelterService.delete(id, token);
    showFeedback('Registro eliminado con éxito');
    loadShelters();
  } catch (err) {
    showFeedback(err.message, 'error');
  }
};

const handleLogout = () => {
  authService.logout();
  isLoggedIn.value = false;
  shelterToEdit.value = null;
  showFeedback('Sesión cerrada correctamente');
};

const showFeedback = (msg, type = 'success') => {
  feedback.value = { message: msg, type };
  setTimeout(() => { feedback.value.message = ''; }, 4000);
};

onMounted(() => {
  loadShelters();
});
</script>

<template>
  <div style="max-width: 1100px; margin: 0 auto; padding: 40px 20px;">
    <!-- Cabecera Hero -->
    <header style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 40px; border-bottom: 1px solid rgba(255, 255, 255, 0.08); padding-bottom: 20px;">
      <div>
        <h1 style="margin: 0; font-size: 2.2rem; font-weight: 800; background: linear-gradient(135deg, #60a5fa 0%, #34d399 100%); -webkit-background-clip: text; -webkit-text-fill-color: transparent;">
          Pyrenean Safe 🏔️
        </h1>
        <p style="margin: 6px 0 0 0; color: #94a3b8; font-size: 0.95rem;">
          Gestión inteligente de refugios y picos del Pirineo
        </p>
      </div>

      <div>
        <div v-if="isLoggedIn" style="display: flex; align-items: center; gap: 12px;">
          <span style="font-size: 0.85rem; background: rgba(16, 185, 129, 0.15); color: #34d399; border: 1px solid rgba(16, 185, 129, 0.3); padding: 6px 12px; border-radius: 20px; font-weight: 600;">
            🟢 Admin Activo
          </span>
          <button @click="handleLogout" style="padding: 7px 14px; background: rgba(239, 68, 68, 0.2); color: #f87171; border: 1px solid rgba(239, 68, 68, 0.3); border-radius: 8px; font-weight: 600; cursor: pointer; transition: background 0.2s;">
            Cerrar Sesión
          </button>
        </div>
      </div>
    </header>

    <!-- Notificación Flotante de Feedback -->
    <div v-if="feedback.message" :style="{ background: feedback.type === 'error' ? 'rgba(239, 68, 68, 0.2)' : 'rgba(16, 185, 129, 0.2)', color: feedback.type === 'error' ? '#f87171' : '#34d399', border: '1px solid currentColor' }" style="padding: 12px 18px; border-radius: 10px; margin-bottom: 24px; font-weight: 500; font-size: 0.95rem;">
      {{ feedback.message }}
    </div>

    <!-- Si no está logueado, mostramos el Login elegante -->
    <div v-if="!isLoggedIn" style="margin: 40px 0;">
      <LoginModal @login-success="isLoggedIn = true; showFeedback('¡Bienvenido, Administrador!')" />
    </div>

    <!-- Si está logueado, mostramos panel de administración (Formulario de crear/editar) -->
    <div v-else>
      <ShelterForm :shelterToEdit="shelterToEdit" @save="handleSaveShelter" @cancel="shelterToEdit = null" />
    </div>

    <!-- Listado principal -->
    <main style="margin-top: 30px;">
      <ShelterList :shelters="shelters" :isAdmin="isLoggedIn" @edit="(s) => shelterToEdit = s" @delete="handleDeleteShelter" />
    </main>
  </div>
</template>