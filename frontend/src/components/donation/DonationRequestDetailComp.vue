<template>
<v-container fluid class="mt-5 px-0">
  <!-- 카테고리 -->
  <v-row justify="center">
    <v-col cols="10" class="pb-0 ml-5">
      <h4 style="font-weight:bold;">
        <v-icon>mdi-arrow-right-box</v-icon>
        {{ donation.category }}
      </h4>
    </v-col>
  </v-row>
  <!-- 제목 -->
  <v-row justify="center">
    <v-col cols="10" class="pt-0 ml-5">
      <h1 class="font-weight-bold">{{ donation.title }}</h1>
    </v-col>
  </v-row>
  <!-- 사진 carousel -->
  <v-row justify="center" style="margin-bottom:100px;">
    <v-col cols="10" class="p-0">
      <v-carousel width="100%">
        <v-carousel-item
          v-for="(item,i) in images"
          :key="i"
          reverse-transition="fade-transition"
          transition="fade-transition"
        >
          <img :src="getImg(item)" style="height:100%; width:100%;" />
        </v-carousel-item>
      </v-carousel>
    </v-col>
  </v-row>
  <!-- 내용 -->
  <v-row class="d-flex justify-content-center col-12 m-0">
    <v-tabs
      background-color="#fffbe6"
      class="pt-0 pr-0 col-md-10 col-sm-12"
      color="black"
      style="position:sticky;top:100px;z-index:5;"
    >
      <v-tab>
        <a
          class="nav-link"
          href=".section1"
          @click="scrollSpy"
          style="color:black;font-weight:bold;"
        >상세내용</a>
      </v-tab>
      <v-tab>
        <a
          class="nav-link"
          href="#section2"
          @click="scrollSpy"
          style="color:black;font-weight:bold;"
        >주소 & 지도</a>
      </v-tab>
      <v-tab>
        <a
          class="nav-link"
          href="#section3"
          @click="scrollSpy"
          style="color:black;font-weight:bold;"
        >응원메세지</a>
      </v-tab>
    </v-tabs>
    <v-col cols="8" md="7">
      <body data-spy="scroll" data-target=".navbar" data-offset="50">
        <div class="section1 container-fluid" style="padding-top:70px;padding-bottom:70px">
          <h3 style="font-family: 'Nanum Gothic';font-weight:bold;">상세내용</h3>
          {{ donation.content }}
        </div>
        <div id="section2" class="container-fluid" style="padding-top:70px;padding-bottom:70px">
          <h3 style="font-family: 'Nanum Gothic';font-weight:bold;">주소 & 지도</h3>
          {{ donation.address }}
        </div>
        <div id="section3" class="container-fluid" style="padding-top:70px;padding-bottom:70px">
          <h3 style="font-family: 'Nanum Gothic';font-weight:bold;">응원메세지</h3>
          <v-list-item v-for="(msg, i) in msgs" :key="i" class="px-0">
            <v-list-item-content>
              <small>{{ msg.userId }}</small>
              <v-row>
                <v-col cols="9">
                  <h5 v-text="msg.msg" class="m-0"></h5>
                </v-col>
                <v-col cols="1" class="d-flex justify-content-center">
                  <v-btn icon color="green" v-if="uid == msg.userId" @click="clickModify(msg)">
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
                  <v-btn
                    icon
                    color="pink"
                    v-if="uid == msg.userId"
                    @click="clickDelete(msg.cheerupid)"
                  >
                    <v-icon>mdi-delete-outline</v-icon>
                  </v-btn>
                </v-col>
              </v-row>
            </v-list-item-content>
          </v-list-item>

          <!-- 메세지 삭제 dialog -->
          <v-dialog v-model="deleteFlag" max-width="290">
            <v-card>
              <v-card-title class="headline">
                <h5>
                  <small>메세지를 삭제하시겠습니까?</small>
                </h5>
              </v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="red darken-1" text @click="deleteMsg(deleteIndex)">Yes</v-btn>
                <v-btn color="blue darken-1" text @click="deleteFlag = false">No</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

          <!-- 메세지 수정 dialog -->
          <v-dialog v-model="modifyFlag" max-width="450">
            <v-card>
              <v-card-title class="headline">
                <h5>
                  <small>메세지 수정</small>
                </h5>
              </v-card-title>
              <v-card-text>
                <v-text-field v-model="tempMsg.msg" :counter="50"></v-text-field>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="modifyMsg(tempMsg)">수정</v-btn>
                <v-btn color="red darken-1" text @click="modifyFlag=false, tempMsg.msg=temp">취소</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

          <!-- 메세지 입력 -->
          <v-text-field
            type="text"
            :counter="50"
            prepend-icon="mdi-comment"
            name="cheerup"
            label="응원의 메세지"
            v-model="cheerup"
          ></v-text-field>
          <v-btn class="mt-2 green-mbtn" style="float:right" @click="msgSubmit">submit</v-btn>
        </div>
      </body>
    </v-col>
    <!-- 플로팅 메뉴 -->
    <v-col cols="4" md="3">
      <v-card
        class="mx-auto"
        max-width="344"
        height="auto"
        outlined
        elevation="3"
        style="position:sticky;top:200px;z-index:5"
      >
        <v-list-item three-line>
          <v-list-item-content>
            <div class="mb-4">
              <p>To. {{ donation.receiver }}</p>
            </div>
            <v-list-item-title class="headline mb-1">{{slider}}%</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <br />
        <v-slider v-model="slider" :thumb-size="24" thumb-label="always" readonly color="#356859">
          <template
            v-slot:thumb-label="{ value }"
          >{{ satisfactionEmojis[Math.min(Math.floor(value / 8), 7)] }}</template>
        </v-slider>

        <div class="p-3">
          <p>현재: {{ donation.nowcnt }}장</p>
          <p>목표: {{ donation.maxcnt }}장</p>
          <div class="d-flex text-center justify-content-start align-items-center">
          <p class="m-0">마감일: {{ donation.edate }}</p>
          <v-chip class="ma-2 font-weight-bold " color="#c4302b" x-small style="color:white;">D-{{ this.Dday }}</v-chip>
          </div>
        </div>
        <div style="text-align:center" class="mb-5">
          <v-btn rounded class="green-mbtn" @click="goDonation">웃음기부</v-btn>
        </div>
      </v-card>
    </v-col>
  </v-row>

  <div class="text-center ma-2">
    <v-snackbar v-model="alertFlag" top right :timeout="3000" color="error">
      로그인이 필요합니다.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="goLogin">Login</v-btn>
      </template>
    </v-snackbar>
  </div>

  <div class="text-center ma-2">
    <v-snackbar v-model="msgFlag" top right :timeout="3000" color="error">
      메세지를 입력해주세요.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="msgFlag=false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</v-container>
</template>

<script>
import http from "@/util/http-common.js";
import { mapGetters, mapState } from "vuex";

export default {
  name: "DonationRequestDetailComp",

  data() {
    return {
      donation: [],
      donationid: 0,
      images: [],
      title: "",
      satisfactionEmojis: ["😐", "🙂", "😊", "😁", "😁", "😄", "😄", "😍"],
      slider: 0,
      Dday: 0,
      cheerup: "",
      msgs: [],
      log: "",
      alertFlag: false,
      uid: "",
      deleteFlag: false,
      deleteIndex: 0,
      modifyFlag: false,
      tempMsg: [],
      temp: "",
      msgFlag: false
    };
  },

  created() {
    this.uid = this.getUserID;
    this.donationid = this.$route.params.ID;
    this.init();
    this.getMsg();
  },

  methods: {
    init() {
      http
        .get("/donation/detail/" + this.donationid)
        .then(res => {
          this.donation = res.data;
          this.slider = (this.donation.nowcnt * 100) / this.donation.maxcnt;
          this.images = this.donation.img
            .substring(0, this.donation.img.length - 1)
            .split("|");
          this.calDay();
        })
        .catch(err => {
          console.log(err);
        });
    },
    getMsg() {
      http
        .get("/cheerup/getList/" + this.donationid)
        .then(res => {
          this.msgs = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    calDay() {
      var now = new Date();
      var endDate = this.donation.edate.split("-");
      var newEnd = new Date(endDate[0], Number(endDate[1]) - 1, endDate[2]);
      var gap = Math.ceil(
        (newEnd.getTime() - now.getTime()) / 1000 / 60 / 60 / 24
      );
      this.Dday = gap;
    },
    scrollSpy(evt) {
      evt.preventDefault();
      const href = evt.target.getAttribute("href");
      var location = document.querySelector(href).offsetTop;
      window.scrollTo({ top: location + 790, behavior: "smooth" });
    },
    msgSubmit() {
      if (this.uid != "") {
        if (this.cheerup != "") {
          http
            .post("/cheerup/writeMsg", {
              donationid: this.donationid,
              msg: this.cheerup,
              userId: this.uid
            })
            .then(res => {
              this.log = res;
              this.cheerup = "";
              this.getMsg();
            })
            .catch(err => {
              console.log(err);
            });
        } else {
          this.msgFlag = true;
        }
      } else {
        this.cheerup = "";
        this.alertFlag = true;
      }
    },
    clickDelete(v) {
      this.deleteIndex = v;
      this.deleteFlag = true;
    },
    clickModify(v) {
      this.tempMsg = v;
      this.temp = v.msg;
      this.modifyIndex = v.cheerupid;
      this.modifyFlag = true;
    },
    deleteMsg(v) {
      this.deleteFlag = false;
      http
        .delete("/cheerup/deleteMsg/" + v)
        .then(res => {
          this.log = res;
          this.getMsg();
        })
        .catch(err => {
          console.log(err);
        });
    },
    modifyMsg(v) {
      this.modifyFlag = false;
      if (v.msg != "") {
        http
          .put("/cheerup/modifyMsg/", {
            donationid: v.donationid,
            cheerupid: v.cheerupid,
            msg: v.msg,
            userId: v.userId
          })
          .then(res => {
            this.log = res;
            this.getMsg();
          })
          .catch(err => {
            console.log(err);
          });
      } else {
        this.msgFlag = true;
        this.getMsg();
      }
    },
    goDonation() {
      if (this.uid != "") {
        this.$router.push({
          name: "SmileCreate",
          params: { ID: this.donationid }
        });
      } else {
        this.alertFlag = true;
      }
    },
    goLogin() {
      this.alertFlag = false;
      this.$router.push("/login");
    },
    getImg(img) {
      return "../../../contents/" + img;
    }
  },
  watch: {},
  computed: {
    ...mapGetters(["getUserID"]),
    ...mapState({
      userID: state => `${state.user.getUserID}`
    })
  }
};
</script>
