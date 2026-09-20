<script setup>
import { ref, watch } from 'vue';

const props = defineProps({
  shelterToEdit: Object
});
const emit = defineEmits(['save', 'cancel']);

const form = ref({
  name: '',
  type: 'SHELTER',
  region: '',
  elevation: '',
  latitude: '',
  longitude: ''
});

watch(() => props.shelterToEdit, (val) => {
  if (val) {
    form.value = { ...val };
  } else {
    resetForm();
  }
}, { immediate: true });

function resetForm() {
  form.value = { name: '', type: 'SHELTER', region: '', elevation: '', latitude: '', longitude: '' };
}

const onSubmit = () => {
  emit('save', {
    ...form.value,
    elevation: Number(form.value.elevation),
    latitude: Number(form.value.latitude),
    longitude: Number(form.value.longitude)
  });
  if (!props.shelterToEdit) resetForm();
};
</script>

<template>
  <div style="background: rgba(30, 41, 59, 0.8); backdrop-filter: blur(12px); border: 1px solid rgba(255, 255, 255, 0.1); padding: 24px; border-radius: 16px; margin-bottom: 30px; box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3);">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px;">
      <h3 style="margin: 0; color: #f8fafc; font-size: 1.1rem;">
        {{ shelterToEdit ? '✏️ Editar Registro #' + shelterToEdit.id : '➕ Añadir Nuevo Pico o Refugio' }}
      </h3>
      <button v-if="shelterToEdit" @click="$emit('cancel')" style="background: transparent; border: none; color: #94a3b8; cursor: pointer; font-size: 0.9rem;">✕ Cancelar</button>
    </div>

    <form @submit.prevent="onSubmit" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 14px;">
      <input v-model="form.name" placeholder="Nombre (ej. Aneto)" required style="padding: 10px 14px; background: #0f172a; border: 1px solid #334155; border-radius: 8px; color: white;" />
      
      <select v-model="form.type" style="padding: 10px 14px; background: #0f172a; border: 1px solid #334155; border-radius: 8px; color: white;">
        <option value="SHELTER">Refugio</option>
        <option value="PEAK">Pico</option>
      </select>

      <input v-model="form.region" placeholder="Región (ej. Benasque)" required style="padding: 10px 14px; background: #0f172a; border: 1px solid #334155; border-radius: 8px; color: white;" />
      
      <input v-model="form.elevation" type="number" placeholder="Elevación (m)" required style="padding: 10px 14px; background: #0f172a; border: 1px solid #334155; border-radius: 8px; color: white;" />
      
      <input v-model="form.latitude" type="number" step="any" placeholder="Latitud" required style="padding: 10px 14px; background: #0f172a; border: 1px solid #334155; border-radius: 8px; color: white;" />
      
      <input v-model="form.longitude" type="number" step="any" placeholder="Longitud" required style="padding: 10px 14px; background: #0f172a; border: 1px solid #334155; border-radius: 8px; color: white;" />

      <div style="grid-column: 1 / -1; display: flex; justify-content: flex-end; gap: 10px; margin-top: 6px;">
        <button type="submit" style="padding: 10px 20px; background: linear-gradient(135deg, #10b981 0%, #059669 100%); color: white; border: none; border-radius: 8px; font-weight: 600; cursor: pointer;">
          {{ shelterToEdit ? 'Actualizar Cambios' : 'Guardar Registro' }}
        </button>
      </div>
    </form>
  </div>
</template>