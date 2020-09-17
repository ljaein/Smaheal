<template>
  <v-container fluid class="pt-5 px-0 col-md-10" style="background-color:#fffbe6;">
    <v-row class="d-flex justify-content-center">
      <v-col cols="5" class="mr-2">
        <v-carousel style="width:30rem;height:23rem;">
          <v-carousel-item v-for="(item,i) in inputFiles" :key="i">
            <img :src="addCarousel(item)" style="width:30rem;height:23rem;" />
          </v-carousel-item>
        </v-carousel>
      </v-col>
      <v-col cols="5">
        이미지
        <v-file-input
          ref="file"
          accept="image/png, image/jpeg, image/bmp"
          v-model="inputFiles"
          color="#356859"
          counter
          multiple
          prepend-icon
          outlined
        >
          <template v-slot:selection="{ text }">
            <v-chip color="#356859" dark label small>{{ text }}</v-chip>
            <!-- 파일 개수 제한 추가 구현하기 -->
          </template>
        </v-file-input>
        <!-- 제목 -->
        제목
        <v-text-field
          v-model="DonationCreate.title"
          outlined
          dense
          :rules="[v => !!v || '제목 입력하세요']"
          required
        ></v-text-field>
        <v-col class="d-flex justify-content-between p-0">
          <!-- 카테고리 -->
          <v-col class="p-0 mr-3">
            카테고리
            <v-select
              v-model="DonationCreate.category"
              :items="items"
              :rules="[v => !!v || '카테고리를 선택하세요']"
              dense
              outlined
              class="p-0"
            ></v-select>
          </v-col>
          <!-- 기부 대상 -->
          <v-col class="p-0">
            기부대상
            <v-text-field
              v-model="DonationCreate.receiver"
              outlined
              dense
              class="p-0"
              :rules="[v => !!v || '기부대상을 입력하세요']"
              required
            ></v-text-field>
          </v-col>
        </v-col>
        <v-col class="d-flex justify-content-between p-0">
          <!-- 마감일 -->
          <v-col class="p-0 mr-3">
            마감일
            <v-menu
              v-model="menu2"
              :close-on-content-click="false"
              :nudge-right="40"
              transition="scale-transition"
              offset-y
              min-width="290px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  class="p-0"
                  v-model="DonationCreate.edate"
                  outlined
                  dense
                  readonly
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="DonationCreate.edate" @input="menu2 = false"></v-date-picker>
            </v-menu>
          </v-col>
          <!--마감개수-->
          <v-col class="p-0">
            마감개수
            <v-col class="d-flex p-0">
              <v-btn icon color="lightgray">
                <v-icon>mdi-arrow-up-drop-circle</v-icon>
              </v-btn>
              <v-text-field
                v-model="DonationCreate.maxcnt"
                class="p-0"
                outlined
                dense
                :rules="[v => !!v || '마감개수를 입력하세요']"
                required
              ></v-text-field>
              <v-btn icon color="lightgray">
                <v-icon>mdi-arrow-down-drop-circle</v-icon>
              </v-btn>
            </v-col>
          </v-col>
        </v-col>
      </v-col>
    </v-row>
    <v-row class="d-flex justify-content-center p-0">
      <!-- 대상 주소 -->
      <v-col cols="10" class="pt-0 pb-0">
        주소
        <v-col class="d-flex justify-content-between p-0 align-items-top">
          <v-text-field v-model="addr" outlined dense class="mr-2"></v-text-field>
          <v-text-field v-model="addrDetail" label="상세주소" outlined dense class="mr-2"></v-text-field>
          <v-btn
            @click="searchAddr()"
            outlined
            style="border:2px solid lightgray; font-weight:bold; margin-top:2px; border-radius:15px;"
          >우편번호 찾기</v-btn>
        </v-col>
      </v-col>
    </v-row>
    <v-row class="d-flex justify-content-center p-0">
      <v-col cols="10" class="p-0">
        <hr />
      </v-col>
    </v-row>
    <!-- 상세내용 -->
    <v-row class="d-flex justify-content-center">
      <v-col cols="10" class="pt-0 pb-0">
        <span>상세내용</span>
        <v-textarea
          v-model="DonationCreate.content"
          auto-grow
          outlined
          rows="20"
          :rules="[v => !!v || '내용을 입력하세요']"
        ></v-textarea>
      </v-col>
    </v-row>
    <v-row cols="8" class="d-flex justify-content-center">
      <v-btn @click="saveDonation()" class="green-mbtn mr-3">임시저장</v-btn>
      <v-btn @click="registDonation()" class="green-mbtn">등록</v-btn>
    </v-row>
  </v-container>
</template>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
import http from "@/util/http-common.js";
import http3 from "@/util/http-common3.js";
import { mapGetters, mapState } from "vuex";

export default {
  name: "DonationRequestCreateComp",

  data() {
    return {
      uid: "",
      inputFiles: [],
      items: ["아동/청소년", "어르신", "장애인", "가족/여성", "다문화", "기타"],
      menu2: false,
      addr: "",
      addrDetail: "",
      DonationCreate: {
        title: "",
        writer: "",
        edate: new Date().toISOString().substr(0, 10),
        category: "",
        maxcnt: "",
        receiver: "",
        address: "",
        content: "",
        img: ""
      }
    };
  },

  created() {
    this.DonationCreate.writer = this.getProfile;
  },

  methods: {
    searchAddr() {
      let x = this;
      new daum.Postcode({
        oncomplete: function(data) {
          //   x.addr1 = data.zonecode;
          x.addr = data.address;
          x.addrDetail = data.buildingName;
        }
      }).open();
    },
    addCarousel(file) {
      return URL.createObjectURL(file);
    },
    registDonation() {
      for (var i = 0; i < this.inputFiles.length; i++) {
        var formData = new FormData();
        const file = this.inputFiles[i];
        if (file != null) {
          if (i != this.inputFiles.length - 1) {
            formData.append("file", file);
            http3
              .post("/makeImageSrc", formData)
              .then(res => {
                this.DonationCreate.img += res.data + "|";
              })
              .catch(err => {
                console.log(err);
              });
          } else {
            formData.append("file", file);
            http3
              .post("/makeImageSrc", formData)
              .then(res => {
                this.DonationCreate.img += res.data + "|";
                this.DonationCreate.address = this.addr + this.addrDetail;
                http
                  .post("/donation/regist", this.DonationCreate)
                  .then(res => {
                    alert("등록 성공");
                    console.log(res.data);
                  })
                  .catch(err => {
                    console.log(err);
                  });
              })
              .catch(err => {
                console.log(err);
              });
          }
        }
      }
    },
    saveDonation() {
      this.DonationCreate.address = this.addr + this.addrDetail;
      http
        .post("/donation/registTemp", this.DonationCreate)
        .then(res => {
          alert("임시 저장 성공");
          console.log(res.data);
        })
        .catch(err => {
          console.log(err);
        });
    },
    onClickImageUpload() {
      this.$refs.file.click();
    },
    onChangeImages(e) {
      const file = e.target.files[0];
      if (file == null) {
        return;
      }
      if (file.size >= 1048576) {
        alert("파일 크기 초과");
        return;
      }
      this.tempimg = URL.createObjectURL(file);
    }
  },
  watch: {},
  computed: {
    ...mapGetters(["getProfile"]),
    ...mapState({
      uname: state => `${state.user.getProfile}`
    })
  }
};
</script>
<style>
.green-mbtn {
  background-color: #356859 !important;
  color: white !important;
  font-weight: bold !important;
}
</style>