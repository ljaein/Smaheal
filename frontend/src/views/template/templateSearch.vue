<template>
  <div v-if="this.items.length != 0" class="d-flex justify-content-center background">
    <div class="mt-5 p-5">
     <v-row class="text-center">
      <v-col style="font-size :2rem;color:white;">
      세 가지 타입을 선택해서 웃음 사진을 볼 수 있습니다.<br>
      보고싶은 타입을 선택하세요!
      </v-col>
    </v-row>
    <v-row class="d-flex justify-content-center mt-3">
      <!-- <v-col> 총 {{ this.totalCnt }}개의 사진에 대한 결과입니다. </v-col> -->
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            large
            v-bind="attrs"
            v-on="on"
            class="tvtn mr-5"
            dark
            color="orange"
            @click="over1 = true"
            >LINE</v-btn
          >
        </template>
        <span>응원메세지를 함께 볼 수 있어요!</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            large
            v-bind="attrs"
            v-on="on"
            class="tvtn mr-5"
            dark
            color="green"
            @click="over2 = true"
            >BOOK</v-btn
          >
        </template>
        <span>책처럼 넘겨서 볼 수 있어요!</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            large
            v-bind="attrs"
            v-on="on"
            class="tvtn mr-5"
            dark
            color="red"
            @click="over3 = true"
            >HEART</v-btn
          >
        </template>
        <span>예쁜 하트모양으로 볼 수 있어요!</span>
      </v-tooltip>
    </v-row>
    </div>
   
    <v-overlay :value="over1" :opacity="1" class="col-12 p-0">
      <v-col class="text-right">
        <v-btn icon @click="over1 = false"
          ><v-icon>mdi-close-circle</v-icon></v-btn
        >
      </v-col>
      <v-col>
        <fallImageComp :propsItem="items" v-if="items.length !== 0" />
      </v-col>
    </v-overlay>
    <v-overlay :value="over2" :opacity="1" class="col-12 p-0">
      <v-col class="text-right">
        <v-btn icon @click="over2 = false"
          ><v-icon>mdi-close-circle</v-icon></v-btn
        >
      </v-col>
      <div class=" justify-content-center col-12">
        <FlipBookComp :propsItem="items" v-if="items.length !== 0" />
      </div>
    </v-overlay>

    <v-overlay :value="over3" :opacity="1" class="col-12 p-0">
      <v-col class="text-right">
        <v-btn icon @click="over3 = false"
          ><v-icon>mdi-close-circle</v-icon></v-btn
        >
      </v-col>
      <v-col class=" justify-content-center col-12">
        <HeartComp :propsItem="items" v-if="items.length !== 0" />
      </v-col>
    </v-overlay>
  </div>
  <v-container v-else style="text-align: center;">
    <v-row>
      <v-col class="h1 font-weight-bold"> (*ㅠㅅㅠ*) </v-col>
    </v-row>
    <v-row>
      <v-col>기부된 사진이 없습니다.</v-col>
    </v-row>
  </v-container>
</template>

<script>
import fallImageComp from "@/components/template/fallImageComp.vue";
import http from "@/util/http-common.js";
import FlipBookComp from "@/components/template/FlipBookComp.vue";
import HeartComp from "@/components/template/HeartComp.vue";

export default {
  name: "templateSearch",
  components: {
    fallImageComp,
    FlipBookComp,
    HeartComp
  },
  data: function() {
    return {
      keyword: "",
      items: [],
      totalCnt: 0,
      over1: false,
      over2: false,
      over3: false
    };
  },
  created() {
    this.keyword = this.$route.query.template;
    http.get(`/template/search/${this.keyword}`).then(({ data }) => {
      this.items = data;

      //   http.get(`/template/totalCount/${this.keyword}`).then(({ data }) => {
      //     this.totalCnt = data;
      //   });
    });
  }
};
</script>
<style>
.tvtn {
  font-weight: bold;
  border-radius: 25px;
}
.background{
  background-image : linear-gradient( rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5) ),url('./../../../public/images/smilesearch.jpg');
  /* background-image :url('./../../../public/images/smilesearch.jpg'); */
  background-size: cover;
  height:700px;
}
</style>