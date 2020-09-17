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
        <div
          class="col-12 col-sm-6 col-md-3"
          v-for="(donation,index) in donationList[i-1]"
          :key="index"
        >
          <v-card max-width="400" style="overflow:hidden;">
            <div style="height:200px;width:100%;">
              <img
                class="donation-img white--text"
                :src="getImg(donation.img)"
                @click="getDetail(donation.donationid)"
              >
            </div>
            <v-card-title
              class="pb-0 mb-1 donation-title"
              @click="getDetail(donation.donationid)"
            >{{donation.title}}</v-card-title>

            <v-card-text class="text--primary">
              <div class="mb-3">{{donation.receiver}}</div>

              <v-progress-linear
                rounded
                :value="toPercent(donation.nowcnt,donation.maxcnt)"
                height="10"
                color="yellow darken-2"
              ></v-progress-linear>

              <div class="d-flex justify-content-between mt-2">
                <div
                  class="d-flex"
                  style="font-size:large; color:#F9A825"
                >{{toPercent(donation.nowcnt,donation.maxcnt)}}%</div>
                <div class="d-flex" style="font-size:large;">{{donation.nowcnt}}장</div>
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
      all: [],
      child: [],
      senior: [],
      disabled: [],
      woman: [],
      multic: [],
      others: []
    };
  },

  created() {
    this.init();
  },

  methods: {
    init() {
      http
        .get("/donation/getCategoryList2")
        .then(res => {
          console.log(res.data);
          this.donationList = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getDetail(donationid) {
      scroll(0,0)
      this.$router.push({
        name: "DonationRequestDetail",
        params: { ID: donationid }
      });
    },
    toPercent(now, max) {
      var percent = (now / max) * 100;
      return Math.round(percent * 10) / 10.0;
    },
    getImg(img){
      img = img.split("|")[0];
      return "../../../contents/" + img;
    },
  },
  watch: {}
};
</script>
<style scoped>
.donation-img {
  height: 200px !important;
  width: 100% !important;
  /* object-fit: cover; */
  transform: scale(1);
  transition: all 0.3s ease-in-out;
}

.donation-img:hover {
  transform: scale(1.1);
  transition: all 0.3s ease-in-out;
  cursor: pointer;
}

.donation-title {
  cursor: pointer;
  height: 70px;
  align-items: baseline;
  text-overflow: ellipsis;
  overflow: hidden;
  line-height: 1.8rem;
}
</style>