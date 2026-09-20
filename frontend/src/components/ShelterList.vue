<script setup>
defineProps({
  shelters: Array,
  isAdmin: Boolean
});
defineEmits(['edit', 'delete']);
</script>

<template>
  <div>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
      <h2 style="margin: 0; font-size: 1.5rem; color: #f8fafc; font-weight: 700;">
        🏔️ Refugios y Picos Registrados
      </h2>
      <span style="font-size: 0.85rem; color: #94a3b8; background: #1e293b; padding: 6px 12px; border-radius: 20px; border: 1px solid #334155;">
        {{ shelters.length }} elementos
      </span>
    </div>

    <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 16px;">
      <div v-for="shelter in shelters" :key="shelter.id" style="background: rgba(30, 41, 59, 0.6); backdrop-filter: blur(8px); border: 1px solid rgba(255, 255, 255, 0.08); border-radius: 12px; padding: 20px; display: flex; flex-direction: column; justify-content: space-between; transition: transform 0.2s, border-color 0.2s;" class="shelter-card">
        <div>
          <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 10px;">
            <h4 style="margin: 0; font-size: 1.15rem; color: #f1f5f9;">{{ shelter.name }}</h4>
            <span :style="{ background: shelter.type === 'PEAK' ? 'rgba(59, 130, 246, 0.2)' : 'rgba(16, 185, 129, 0.2)', color: shelter.type === 'PEAK' ? '#60a5fa' : '#34d399', border: '1px solid currentColor' }" style="font-size: 0.7rem; padding: 2px 8px; border-radius: 6px; font-weight: 700; text-transform: uppercase;">
              {{ shelter.type === 'PEAK' ? 'Pico' : 'Refugio' }}
            </span>
          </div>
          <p style="margin: 0 0 14px 0; color: #94a3b8; font-size: 0.9rem; display: flex; align-items: center; gap: 6px;">
            📍 <span>{{ shelter.region }}</span> • <span style="color: #cbd5e1; font-weight: 600;">{{ shelter.elevation }}m</span>
          </p>
        </div>

        <div v-if="isAdmin" style="display: flex; gap: 8px; border-top: 1px solid rgba(255, 255, 255, 0.06); padding-top: 12px; margin-top: 10px;">
          <button @click="$emit('edit', shelter)" style="flex: 1; padding: 7px; background: #334155; color: #f8fafc; border: none; border-radius: 6px; font-size: 0.85rem; font-weight: 500; cursor: pointer; transition: background 0.2s;">
            ✏️ Editar
          </button>
          <button @click="$emit('delete', shelter.id)" style="flex: 1; padding: 7px; background: rgba(239, 68, 68, 0.2); color: #f87171; border: 1px solid rgba(239, 68, 68, 0.3); border-radius: 6px; font-size: 0.85rem; font-weight: 500; cursor: pointer; transition: background 0.2s;">
            🗑️ Eliminar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.shelter-card:hover {
  transform: translateY(-2px);
  border-color: rgba(59, 130, 246, 0.4);
}
</style>