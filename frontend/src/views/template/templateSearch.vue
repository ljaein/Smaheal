<template>
  <div v-if="this.items.length != 0">
    <v-row class="d-flex justify-content-center mt-10">
      <!-- <v-col> 총 {{ this.totalCnt }}개의 사진에 대한 결과입니다. </v-col> -->
        <v-btn class="tvtn mr-5" dark color="orange" @click="over1 = true"
          >타임라인</v-btn
        >
        <v-btn class="tvtn mr-5" dark color="green" @click="over2 = true"
          >Book</v-btn
        >
        <v-btn class="tvtn" dark color="red" @click="over3 = true"
          >하트모양</v-btn
        >
    </v-row>
    <v-overlay
      :value="over1"
      :opacity="1"
      class="col-12 p-0"
    >
    </v-overlay>
    <v-overlay
      :value="over1"
      :opacity="1"
      :absolute="true"
    >
    <v-col class="text-right">
      <v-btn icon @click="over1=false" ><v-icon>mdi-close-circle</v-icon></v-btn>
    </v-col>
      <v-col>
        <fallImageComp :propsItem="items" v-if="items.length !== 0" />
      </v-col>
    </v-overlay>
    <v-overlay
      :value="over2"
      :opacity="1"
      class="col-12 p-0"
    >
    <v-col class="text-right">
      <v-btn icon @click="over2=false" ><v-icon>mdi-close-circle</v-icon></v-btn>
    </v-col>
      <div class=" justify-content-center col-12">
        <FlipBookComp :propsItem="items" v-if="items.length !== 0" />
      </div>
    </v-overlay>

    <v-overlay
      :value="over3"
      :opacity="1"
      class="col-12 p-0"
    >
    <v-col class="text-right">
      <v-btn icon @click="over3=false" ><v-icon>mdi-close-circle</v-icon></v-btn>
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
</style>