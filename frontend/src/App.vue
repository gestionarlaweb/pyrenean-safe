<script setup>
import { ref, onMounted, watch } from 'vue';
import shelterService from './services/shelterService';

const shelters = ref([]);
const loading = ref(false);
const error = ref(null);

// Filtros
const selectedRegion = ref('');
const selectedType = ref('');

// Cargar refugios con filtros opcionales
const loadShelters = async () => {
  loading.value = true;
  error.value = null;
  try {
    const params = {};
    if (selectedRegion.value) params.region = selectedRegion.value;
    if (selectedType.value) params.type = selectedType.value;

    const response = await shelterService.getShelters(params);
    shelters.value = response.data;
  } catch (err) {
    console.error(err);
    error.value = 'No se pudo conectar con el servidor backend.';
  } finally {
    loading.value = false;
  }
};

// Cargar datos al iniciar la app
onMounted(() => {
  loadShelters();
});
</script>

<template>
  <div style="max-width: 900px; margin: 40px auto; font-family: Arial, sans-serif; padding: 0 20px;">
    <header style="display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #eaeaea; padding-bottom: 20px; margin-bottom: 20px;">
      <h1>🏔️ PyreneanSafe</h1>
      <span style="background-color: #e2f0cb; color: #385e00; padding: 6px 12px; border-radius: 20px; font-weight: bold; font-size: 14px;">
        Backend Conectado
      </span>
    </header>

    <!-- Sección de Filtros -->
    <div style="background: #f9f9f9; padding: 15px; border-radius: 8px; margin-bottom: 20px; display: flex; gap: 15px; align-items: center;">
      <div>
        <label style="display: block; font-size: 12px; font-weight: bold; margin-bottom: 5px;">Región:</label>
        <input 
          v-model="selectedRegion" 
          placeholder="Ej: Ordesa" 
          style="padding: 8px; border: 1px solid #ccc; border-radius: 4px;"
        />
      </div>

      <div>
        <label style="display: block; font-size: 12px; font-weight: bold; margin-bottom: 5px;">Tipo:</label>
        <select v-model="selectedType" style="padding: 9px; border: 1px solid #ccc; border-radius: 4px;">
          <option value="">Todos</option>
          <option value="PEAK">Pico (PEAK)</option>
          <option value="SHELTER">Refugio (SHELTER)</option>
        </select>
      </div>

      <div style="align-self: flex-end; display: flex; gap: 10px;">
        <button @click="loadShelters" style="padding: 9px 15px; background: #2c3e50; color: white; border: none; border-radius: 4px; cursor: pointer;">
          Filtrar
        </button>
        <button @click="selectedRegion=''; selectedType=''; loadShelters();" style="padding: 9px 15px; background: #e0e0e0; border: none; border-radius: 4px; cursor: pointer;">
          Limpiar
        </button>
      </div>
    </div>

    <!-- Estados de carga / errores -->
    <div v-if="loading" style="text-align: center; color: #666; padding: 20px;">Cargando datos...</div>
    <div v-if="error" style="background: #ffebee; color: #c62828; padding: 10px; border-radius: 4px; margin-bottom: 20px;">{{ error }}</div>

    <!-- Tabla de resultados -->
    <div v-if="!loading && !error">
      <p style="color: #666; font-size: 14px; margin-bottom: 10px;">Mostrando <strong>{{ shelters.length }}</strong> registros</p>
      
      <table style="width: 100%; border-collapse: collapse; background: white; box-shadow: 0 1px 3px rgba(0,0,0,0.1); border-radius: 6px; overflow: hidden;">
        <thead>
          <tr style="background: #2c3e50; color: white; text-align: left;">
            <th style="padding: 12px;">Nombre</th>
            <th style="padding: 12px;">Tipo</th>
            <th style="padding: 12px;">Región</th>
            <th style="padding: 12px;">Elevación</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in shelters" :key="item.id" style="border-bottom: 1px solid #eaeaea;">
            <td style="padding: 12px; font-weight: bold;">{{ item.name }}</td>
            <td style="padding: 12px;">
              <span :style="{ background: item.type === 'PEAK' ? '#e3f2fd' : '#e8f5e9', color: item.type === 'PEAK' ? '#0d47a1' : '#1b5e20', padding: '4px 8px', borderRadius: '4px', fontSize: '12px', fontWeight: 'bold' }">
                {{ item.type }}
              </span>
            </td>
            <td style="padding: 12px;">{{ item.region }}</td>
            <td style="padding: 12px;">{{ item.elevation }} m</td>
          </tr>
          <tr v-if="shelters.length === 0">
            <td colspan="4" style="text-align: center; padding: 20px; color: #999;">No se encontraron registros.</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>