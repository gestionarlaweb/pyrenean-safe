<script setup>
import { ref } from 'vue';
import authService from '../services/authService';

const emit = defineEmits(['login-success']);
const username = ref('');
const password = ref('');
const error = ref('');
const loading = ref(false);

const handleLogin = async () => {
  loading.value = true;
  error.value = '';
  try {
    await authService.login(username.value, password.value);
    emit('login-success');
  } catch (err) {
    error.value = 'Credenciales incorrectas o servidor inalcanzable';
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div style="background: rgba(30, 41, 59, 0.7); backdrop-filter: blur(12px); border: 1px solid rgba(255, 255, 255, 0.1); padding: 24px; border-radius: 16px; box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3); max-width: 400px; width: 100%; margin: 0 auto;">
    <h3 style="margin-top: 0; color: #f8fafc; font-size: 1.25rem; display: flex; align-items: center; gap: 8px;">
      🔒 Panel de Administración
    </h3>
    <form @submit.prevent="handleLogin" style="display: flex; flex-direction: column; gap: 14px;">
      <div>
        <label style="display: block; font-size: 0.85rem; color: #94a3b8; margin-bottom: 6px;">Usuario</label>
        <input v-model="username" placeholder="admin" required style="width: 100%; padding: 10px 14px; background: #0f172a; border: 1px solid #334155; border-radius: 8px; color: white; outline: none; transition: border-color 0.2s;" />
      </div>
      <div>
        <label style="display: block; font-size: 0.85rem; color: #94a3b8; margin-bottom: 6px;">Contraseña</label>
        <input v-model="password" type="password" placeholder="admin123" required style="width: 100%; padding: 10px 14px; background: #0f172a; border: 1px solid #334155; border-radius: 8px; color: white; outline: none; transition: border-color 0.2s;" />
      </div>
      <button type="submit" :disabled="loading" style="padding: 11px; background: linear-gradient(135deg, #3b82f6 0%, #2563eb 100%); color: white; border: none; border-radius: 8px; font-weight: 600; cursor: pointer; transition: opacity 0.2s;">
        {{ loading ? 'Autenticando...' : 'Iniciar Sesión' }}
      </button>
      <p v-if="error" style="color: #f87171; font-size: 0.85rem; margin: 0; text-align: center;">{{ error }}</p>
    </form>
  </div>
</template>