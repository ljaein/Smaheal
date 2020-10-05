<template>
  <div v-if="this.totalCnt != 0" class="mt-5 container">
    <v-row>
      <v-col> 총 {{ this.totalCnt }}개의 사진에 대한 결과입니다. </v-col>
    </v-row>
    <v-row>
      <!-- <v-col>
        <fallImageComp :propsItem="items" v-if="items.length !== 0" />
      </v-col> -->
      <v-col class="container justify-content-center col-xl-12 col-lg-12 col-md-12 col-sm-12">
        <FlipBookComp :propsItem="items" v-if="items.length !== 0" />
      </v-col>
    </v-row>
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
// import fallImageComp from "@/components/template/fallImageComp.vue";
import http from "@/util/http-common.js";
import FlipBookComp from "@/components/template/FlipBookComp.vue";

export default {
  name: "templateSearch",
  components: {
    // fallImageComp,
    FlipBookComp
  },
  data: function() {
    return {
      keyword: "",
      items: [],
      totalCnt: 0
    };
  },
  created() {
    this.keyword = this.$route.query.template;

    http.get(`/template/search/${this.keyword}`).then(({ data }) => {
      this.items = data;

      http.get(`/template/totalCount/${this.keyword}`).then(({ data }) => {
        this.totalCnt = data;
      });
    });
  }
};
</script>