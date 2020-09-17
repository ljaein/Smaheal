<template>
  <v-container fluid class="mt-5 pl-15 pr-15">
    <!-- 카테고리 -->
    <v-toolbar color dark flat prominent>
      <v-row class="d-flex align-items-center mt-5">
        <v-col cols="3"></v-col>
        <v-col cols="6" class="d-flex justify-content-center">
          <v-text-field
            flat
            hide-details
            label="Search"
            prepend-inner-icon="mdi-magnify"
            solo-inverted
          ></v-text-field>
        </v-col>
        <v-col cols="3" class="d-flex justify-content-end">
          <v-btn @click="goCreate()" class="mr-2">게시물 작성</v-btn>
        </v-col>
      </v-row>

      <template v-slot:extension>
        <v-tabs v-model="tab" icons-and-text centered hover:false>
          <v-tabs-slider></v-tabs-slider>
          <v-tab href="#tab-1" @click="infiniteId+=1">전체</v-tab>
          <v-tab href="#tab-2" @click="infiniteId+=1">아동/청소년</v-tab>
          <v-tab href="#tab-3" @click="infiniteId+=1">어르신</v-tab>
          <v-tab href="#tab-4" @click="infiniteId+=1">장애인</v-tab>
          <v-tab href="#tab-5" @click="infiniteId+=1">가족/여성</v-tab>
          <v-tab href="#tab-6" @click="infiniteId+=1">다문화</v-tab>
          <v-tab href="#tab-7" @click="infiniteId+=1">기타</v-tab>
        </v-tabs>
      </template>
    </v-toolbar>

    <v-tabs-items v-model="tab">
      <v-tab-item class="row justify-content-left p-3" value="tab-1">
        <div class="col-12 col-sm-6 col-md-3" v-for="(donation,index) in all" :key="index">
          <v-card max-width="400" style="overflow:hidden;">
            <div style="height:200px;width:100%;">
              <img
                class="donation-img white--text"
                :src="getImg(donation.img)"
                @click="getDetail(donation.donationid)"
              />
            </div>
            <v-card-title
              class="pb-0 mb-1 donation-title"
              @click="getDetail(donation.donationid)"
            >{{donation.title}}{{donationList[0].length}}</v-card-title>
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
      <v-tab-item class="row justify-content-left p-3" value="tab-2">
        <div class="col-12 col-sm-6 col-md-3" v-for="(donation,index) in child" :key="index">
          <v-card max-width="400" style="overflow:hidden;">
            <div style="height:200px;width:100%;">
              <img
                class="donation-img white--text"
                :src="getImg(donation.img)"
                @click="getDetail(donation.donationid)"
              />
            </div>
            <v-card-title
              class="pb-0 mb-1 donation-title"
              @click="getDetail(donation.donationid)"
            >{{donation.title}}{{donationList[0].length}}</v-card-title>
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
      <v-tab-item class="row justify-content-left p-3" value="tab-3">
        <div class="col-12 col-sm-6 col-md-3" v-for="(donation,index) in old" :key="index">
          <v-card max-width="400" style="overflow:hidden;">
            <div style="height:200px;width:100%;">
              <img
                class="donation-img white--text"
                :src="getImg(donation.img)"
                @click="getDetail(donation.donationid)"
              />
            </div>
            <v-card-title
              class="pb-0 mb-1 donation-title"
              @click="getDetail(donation.donationid)"
            >{{donation.title}}{{donationList[0].length}}</v-card-title>
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
      <v-tab-item class="row justify-content-left p-3" value="tab-4">
        <div class="col-12 col-sm-6 col-md-3" v-for="(donation,index) in disabled" :key="index">
          <v-card max-width="400" style="overflow:hidden;">
            <div style="height:200px;width:100%;">
              <img
                class="donation-img white--text"
                :src="getImg(donation.img)"
                @click="getDetail(donation.donationid)"
              />
            </div>
            <v-card-title
              class="pb-0 mb-1 donation-title"
              @click="getDetail(donation.donationid)"
            >{{donation.title}}{{donationList[0].length}}</v-card-title>
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
      <v-tab-item class="row justify-content-left p-3" value="tab-5">
        <div class="col-12 col-sm-6 col-md-3" v-for="(donation,index) in woman" :key="index">
          <v-card max-width="400" style="overflow:hidden;">
            <div style="height:200px;width:100%;">
              <img
                class="donation-img white--text"
                :src="getImg(donation.img)"
                @click="getDetail(donation.donationid)"
              />
            </div>
            <v-card-title
              class="pb-0 mb-1 donation-title"
              @click="getDetail(donation.donationid)"
            >{{donation.title}}{{donationList[0].length}}</v-card-title>
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
      <v-tab-item class="row justify-content-left p-3" value="tab-6">
        <div class="col-12 col-sm-6 col-md-3" v-for="(donation,index) in multi" :key="index">
          <v-card max-width="400" style="overflow:hidden;">
            <div style="height:200px;width:100%;">
              <img
                class="donation-img white--text"
                :src="getImg(donation.img)"
                @click="getDetail(donation.donationid)"
              />
            </div>
            <v-card-title
              class="pb-0 mb-1 donation-title"
              @click="getDetail(donation.donationid)"
            >{{donation.title}}{{donationList[0].length}}</v-card-title>
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
      <v-tab-item class="row justify-content-left p-3" value="tab-7">
        <div class="col-12 col-sm-6 col-md-3" v-for="(donation,index) in others" :key="index">
          <v-card max-width="400" style="overflow:hidden;">
            <div style="height:200px;width:100%;">
              <img
                class="donation-img white--text"
                :src="getImg(donation.img)"
                @click="getDetail(donation.donationid)"
              />
            </div>
            <v-card-title
              class="pb-0 mb-1 donation-title"
              @click="getDetail(donation.donationid)"
            >{{donation.title}}{{donationList[0].length}}</v-card-title>
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
    <!-- infinite loading -->
    <infinite-loading :identifier="infiniteId" @infinite="infiniteHandler" spinner="waveDots">
      <div slot="no-results"></div>
    </infinite-loading>
  </v-container>
</template>

<script>
import http from "@/util/http-common.js";
import InfiniteLoading from "vue-infinite-loading";
export default {
  name: "DonationListComp",
  components: {
    InfiniteLoading
  },
  data() {
    return {
      tab: null,
      donationList: [],
      page: [1, 1, 1, 1, 1, 1, 1],
      isFirst: true,
      infiniteId: 0,
      all: [],
      child: [],
      old: [],
      disabled: [],
      woman: [],
      multi: [],
      others: []
    };
  },

  created() {
    // this.init();
  },

  methods: {
    init() {
      http
        .get("/donation/getList/0")
        .then(res => {
          this.donationList = res.data;
          this.all = this.donationList[0];
          this.child = this.donationList[1];
          this.old = this.donationList[2];
          this.disabled = this.donationList[3];
          this.woman = this.donationList[4];
          this.multi = this.donationList[5];
          this.others = this.donationList[6];
        })
        .catch(err => {
          console.log(err);
        });
    },
    infiniteHandler($state) {
      if (this.isFirst) {
        http
          .get("/donation/getList/0")
          .then(res => {
            this.donationList = res.data;
            this.all = this.donationList[0];
            this.child = this.donationList[1];
            this.old = this.donationList[2];
            this.disabled = this.donationList[3];
            this.woman = this.donationList[4];
            this.multi = this.donationList[5];
            this.others = this.donationList[6];
            $state.loaded();
          })
          .catch(err => {
            console.log(err);
          });
        this.isFirst = false;
      } else {
        var curTab = this.tab.split("-")[1] - 1;
        http
          .get(`/donation/getCategoryList/${curTab}/${this.page[curTab]}`)
          .then(res => {
            setTimeout(() => {
              if (res.data.length != 0) {
                if (curTab == 0) {
                  this.all = this.all.concat(res.data);
                  $state.loaded();
                  this.page[curTab] += 1;
                  if (this.all.length / 8 < 1) {
                    $state.complete();
                  }
                }else if (curTab == 1) {
                  this.child = this.child.concat(res.data);
                  $state.loaded();
                  this.page[curTab] += 1;
                  if (this.child.length / 8 < 1) {
                    $state.complete();
                  }
                }else if (curTab == 2) {
                  this.old = this.old.concat(res.data);
                  $state.loaded();
                  this.page[curTab] += 1;
                  if (this.old.length / 8 < 1) {
                    $state.complete();
                  }
                }else if (curTab == 3) {
                  this.disabled = this.disabled.concat(res.data);
                  $state.loaded();
                  this.page[curTab] += 1;
                  if (this.disabled.length / 8 < 1) {
                    $state.complete();
                  }
                }else if (curTab == 4) {
                  this.woman = this.woman.concat(res.data);
                  $state.loaded();
                  this.page[curTab] += 1;
                  if (this.woman.length / 8 < 1) {
                    $state.complete();
                  }
                }else if (curTab == 5) {
                  this.multi = this.multi.concat(res.data);
                  $state.loaded();
                  this.page[curTab] += 1;
                  if (this.multi.length / 8 < 1) {
                    $state.complete();
                  }
                }else {
                  this.others = this.others.concat(res.data);
                  $state.loaded();
                  this.page[curTab] += 1;
                  if (this.others.length / 8 < 1) {
                    $state.complete();
                  }
                }
              } else {
                $state.complete();
              }
            }, 500);
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    getDetail(donationid) {
      scroll(0, 0);
      this.$router.push({
        name: "DonationRequestDetail",
        params: { ID: donationid }
      });
    },
    toPercent(now, max) {
      var percent = (now / max) * 100;
      return Math.round(percent * 10) / 10.0;
    },
    getImg(img) {
      img = img.split("|")[0];
      return "../../../contents/" + img;
    },
    goCreate() {
      this.$router.push({
        name: "DonationRequestCreate"
      });
    }
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