<template>
    <v-container>
        <flipbook class="flipbook" :pages="['array', 'of', 'image', 'URLs']"/>
    </v-container>
</template>

<script>
import Flipbook from 'flipbook-vue'

export default {
    name: "ebookComp",
    components: {
        Flipbook
    },
    data: function() {
        return {
            
        }
    },
}
</script>

<style>
.flipbook {
  width: 90vw;
  height: 90vh;
}
</style>