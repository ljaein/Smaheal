<template>
  <v-container fluid class="mt-5 pl-15 pr-15">
    <!-- 카테고리 -->
    <v-card color="basil">
      <v-tabs
        v-model="tab"
        background-color="transparent"
        color="basil"
        class="basil--text"
        grow
      >
        <!-- <v-tabs-slider></v-tabs-slider> -->
        <v-tab href="#tab-1" class="tab-text">대기 중인 기부요청</v-tab>
        <v-tab href="#tab-2" class="tab-text">영상 크롤링</v-tab>
      </v-tabs>
    </v-card>

    <v-tabs-items v-model="tab">
      <v-tab-item class="row justify-content-left p-3" value="tab-1">
        <div
          class="col-12 col-sm-6 col-md-3"
          v-for="(donation, index) in waitList"
          :key="index"
        >
          <v-card max-width="400" style="overflow:hidden;">
            <div style="height:150px;width:100%;">
              <img
                class="donation-img white--text"
                :src="getImg(donation.img)"
              />
            </div>
            <v-card-title class="pb-0 mb-1 donation-title">{{
              donation.title
            }}</v-card-title>
            <v-card-text class="text--primary pb-0">
              <div class="mb-3">{{ donation.receiver }}</div>
              <v-col class="d-flex justify-content-end pt-0">
                <v-btn
                  class="green-mbtn px-2"
                  data-toggle="modal"
                  data-target="#waitDetail"
                  @click="goDoWaitDetail(donation)"
                >
                  상세보기
                </v-btn>
              </v-col>
            </v-card-text>
          </v-card>
        </div>
      </v-tab-item>
      <v-tab-item class="row justify-content-left p-3" value="tab-2">
        <!-- 유튜브 크롤링 임시 -->
        <div class="col-12 col-md-6 col-lg-6 col-sm-6 p-5" style="margin:100px auto 0 auto;text-align:center;border:2px solid lightgray; border-radius:8px;">
          <p style="font-size:1.2rem;">YouTube에서 영상을 가져옵니다.</p>
          <v-text-field v-model="word" label="검색어" id="id"></v-text-field>
          <v-select :items="items" label="연령대" dense outlined v-model="age"></v-select>
          <v-btn class="green-mbtn" @click="crawling">크롤링</v-btn>
        </div>
      </v-tab-item>
    </v-tabs-items>
    <WaitDetailComp :wait="this.goWaitDetail" :waitImages="this.goWaitImages" />
  </v-container>
</template>

<script>
import http from "@/util/http-common.js";
import WaitDetailComp from "@/components/admin/WaitDetailComp.vue";
export default {
  name: "AdminPage",
  components: {
    WaitDetailComp
  },
  data() {
    return {
      tab: null,
      waitList: [],
      page: 0,
      goWaitDetail: [],
      goWaitImages: [],
      word: '',
      age: 0,
      items: [1, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100],
    };
  },
  created() {
    this.init();
  },

  methods: {
    goDoWaitDetail(dona) {
      this.goWaitDetail = dona;
      this.goWaitImages = dona.img.substring(0, dona.img.length - 1).split("|");
    },
    init() {
      http
        .get("/donation/getWaitList/0")
        .then(res => {
          this.waitList = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    getDetail(donationid) {
      scroll(0, 0);
      this.$router.push({
        name: "DonationRequestDetail",
        params: { ID: donationid }
      });
    },
    getImg(img) {
      img = img.split("|")[0];
      return "../../../contents/" + img;
    },
    crawling() {
      if (this.age == 0 || this.word == '') {
        alert('크롤링 정보를 입력하세요.');
      } else {
        http
          .post(`/crawling/youtube?search=${this.word}&age=${this.age}`)
          .then((res) => {
            this.log = res.data;
            this.word = '';
            this.age = 0;
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
  }
};
</script>
<style scoped>
.donation-img {
  height: 150px !important;
  width: 100% !important;
}

.donation-title {
  display: block;
  height: 40px;
  /* align-items: baseline; */
  text-overflow: ellipsis;
  overflow: hidden;
  line-height: 1.8rem;
  white-space: nowrap;
  text-align: left;
}
.basil {
  background-color: #fffbe6 !important;
}
.basil--text {
  color: #356859 !important;
}
.tab-text {
  font-weight: bold;
  text-decoration: none;
}
</style>