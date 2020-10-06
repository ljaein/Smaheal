<template>
  <div class="container col-12" align="center" style="width:100%">
    <flipbook class="flipbook" :pages="pages" v-slot="flipbook" :zooms="[1]" align="center">
      <v-btn @click="flipbook.flipLeft">
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
      <v-btn @click="flipbook.flipRight">
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </flipbook>
  </div>
</template>

<script>
import flipbook from "flipbook-vue";

export default {
  name: "FlipBookComp",
  components: { flipbook },
  data() {
    return {
      pages: [null,'/images/firstpage.png','/images/1601259558145.png','/images/1601260096510.png']
    };
  },
  props: {
    propsItem: { type: Array }
  },
  created(){
    // for(var i = 0;i<this.propsItem.length;i++){
    //   this.pages.push('/images/'+this.propsItem[i].photo);
    // }
    // console.log(this.pages)
    // document.getElementsByClassName('page')[0].setAttribute('height','300px');
  },
  updated(){
    console.log(document.getElementsByClassName('page'))

  }
};
</script>

<style>
/* .flipbook {
  max-width: 100%;
  height:600px;
} */
.flipbook {
  position: relative;
  width: 100vw;
  height: 100vh;
  left:0px;
  top:0px;
}

.bounding-box{
  /* width: 1000px !important;
  height: 500px !important;
  left:0px !important;
  top:0px !important; */
}
/* .page[data-v-6c371404]{
  width: 300px !important;
  height: 300px !important;
} */
img{
  /* width: 500px !important;
  height: 500px !important; */
  /* left:0px !important;
  top:0px !important; */
}
/* .viewport{
  width:1000px !important;
  height:500px !important;
} */
/* .page{
  width:300px !important;
  height:300px !important;
  right:0px !important;
}  */
</style>