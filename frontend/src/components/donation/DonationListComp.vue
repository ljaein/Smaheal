<template>
  <v-container fluid class="mt-5 pl-15 pr-15">
    <!-- 카테고리 -->
    <v-tabs
      v-model="tab"
      background-color="deep-purple accent-4"
      centered
      dark
      icons-and-text
      hover:false
    >
      <v-tabs-slider></v-tabs-slider>
      <v-tab href="#tab-1">
        전체
        <v-icon>mdi-phone</v-icon>
      </v-tab>
      <v-tab href="#tab-2">
        아동/청소년
        <v-icon>mdi-heart</v-icon>
      </v-tab>
      <v-tab href="#tab-3">
        어르신
        <v-icon>mdi-account-box</v-icon>
      </v-tab>
      <v-tab href="#tab-4">
        장애인
        <v-icon>mdi-account-box</v-icon>
      </v-tab>
      <v-tab href="#tab-5">
        가족/여성
        <v-icon>mdi-account-box</v-icon>
      </v-tab>
      <v-tab href="#tab-6">
        다문화
        <v-icon>mdi-account-box</v-icon>
      </v-tab>
      <v-tab href="#tab-7">
        기타
        <v-icon>mdi-account-box</v-icon>
      </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item class="row justify-content-left p-3" v-for="i in 7" :key="i" :value="'tab-' + i">
        <!-- 게시물 -->
        <div class="col-12 col-sm-6 col-md-3" v-for="donation in 9" :key="donation">
          <v-card max-width="400" style="overflow:hidden;">
            <div style="overflow:hidden">
              <v-img
                class="donation-img white--text align-end"
                height="200px"
                src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                @click="getDetail(12)"
              ></v-img>
            </div>
            <v-card-title class="pb-0" @click="getDetail(12)" style="cursor:pointer;">재인이에게 웃음이 필요해요</v-card-title>

            <v-card-text class="text--primary">
              <div class="mb-3">싸피복지기관</div>

              <v-progress-linear rounded value="30" height="10" color="yellow darken-2"></v-progress-linear>

              <div class="d-flex justify-content-between mt-2">
                <div class="d-flex" style="font-size:large; color:#F9A825">10%</div>
                <div class="d-flex" style="font-size:large;">22장</div>
              </div>
            </v-card-text>
          </v-card>
        </div>
      </v-tab-item>
    </v-tabs-items>
  </v-container>
</template>

<script>
import http from "@/util/http-common.js";

export default {
  name: "DonationListComp",

  data() {
    return {
      tab: null,
      donationList: [],
      category: ""
    };
  },

  created() {
    this.init();
  },

  methods: {
    init() {
      http
        .get("/donation/getList")
        .then(res => {
          this.donationList = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getDetail(donationid) {
      this.$router.push({
        name: "DonationRequestDetail",
        params: { ID: donationid }
      });
    },
    listByCategory(i) {
      if (i == 1) {
        http
          .get("/donation/getList")
          .then(res => {
            this.donationList = res.data;
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        if (i == 2) this.category = "아동/청소년";
        else if (i == 3) this.category = "어르신";
        else if (i == 4) this.category = "장애인";
        else if (i == 5) this.category = "가족/여성";
        else if (i == 6) this.category = "다문화";
        else if (i == 7) this.category = "기타";
        http
          .get("/donation/getCategoryList/" + this.category)
          .then(res => {
            this.donationList = res.data;
          })
          .catch(err => {
            console.log(err);
          });
      }
      return this.donationList;
    }
  },
  watch: {}
};
</script>
<style scoped>
.donation-img {
  height: 100%;
  width: 100%;
  object-fit: cover;
  transform: scale(1);
  transition: all 0.3s ease-in-out;
}

.donation-img:hover {
  transform: scale(1.1);
  transition: all 0.3s ease-in-out;
  cursor: pointer;
}
</style>