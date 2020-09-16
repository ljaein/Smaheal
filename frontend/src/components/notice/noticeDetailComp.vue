<template>
  <v-container>
    <div>
        {{this.item.title}}
    </div>
    <div>
        {{this.item.content}}
    </div>
  </v-container>
</template>

<script>

export default {
  name: "noticeDetailComp",
  props: {
      propItem: {type: Object}
  },
  data(){
   return {
       item: this.propItem,
   };
  },
};
</script>
