<template>
  <div>
    <v-form>
      <v-container fluid>
        <v-row justify="space-around">
          <v-col cols="12" md="6">
            <input ref="imageInput" type="file" hidden @change="onChangeImages" />
            <v-btn
              class="mb-2"
               style="border:2px solid lightgray; font-weight:bold; border-radius:15px;"
              type="button"
              @click="onClickImageUpload"
              outlined
            >이미지 업로드</v-btn>
            <div style="width:20rem;height:15rem;border-radius:5px;border:1px solid lightgray">
              <img v-if="imageUrl" :src="imageUrl" style="width:20rem;height:15rem;border-radius:5px;" />
            </div>
            <v-text-field v-model="title" label="제목" required></v-text-field>
          </v-col>
        </v-row>
        <v-row justify="space-around">
          <v-col cols="12" md="6">
            <v-textarea
              auto-grow
              v-model="content"
              label="내용"
              outlined
              required
              rows="10"
              row-height="30"
            ></v-textarea>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
    <div style="text-align:center;">
      <v-btn v-if="isModify" @click="updateReview" outlined>수정</v-btn>
      <v-btn v-else @click="writeReview" class="green-mbtn mr-2">등록</v-btn>
      <v-btn @click="goBack"  class="green-mbtn">뒤로가기</v-btn>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import http3 from "@/util/http-common3.js";
import { mapGetters } from "vuex";

export default {
  name: "ReviewWriteComp",
  data() {
    return {
      title: "",
      content: "",
      img: "",
      fileInfo: "",
      imageUrl: "",
      item: {},
      isModify: false
    };
  },
  created() {
    if (this.$route.params.num) {
      this.isModify = true;
      http
        .get(`/review/get/${this.$route.params.num}`)
        .then(({ data }) => {
          this.item = data;
          this.title = data.title;
          this.content = data.content;
          this.img = data.img;
          if (this.getProfile != data.nickName) {
            this.$router.push(`/reviewList`);
            alert("잘못된 경로입니다.");
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  computed: {
    ...mapGetters(["getProfile"])
  },
  methods: {
    postReview(data) {
      http
        .post(`/review/regist`, {
          title: this.title,
          content: this.content,
          nickName: this.getProfile,
          img: data,
          donationid: this.$route.params.donationid
        })
        .then(({ data }) => {
          console.log(data);
          if (data == "success") {
            alert("성공적으로 등록 되었습니다.");
            this.$router.push(`/reviewList`);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    writeReview() {
      if (this.fileInfo != "") {
        var formData = new FormData();
        formData.append("img", this.fileInfo);
        http3
          .post(`/review/img`, formData)
          .then(({ data }) => {
            this.postReview(data);
          })
          .catch(e => {
            if (e.request.status === 404) {
              this.alertMsg = "등록 처리시 에러가 발생했습니다.";
              this.alert = true;
            } else {
              this.$router.push(`/apierror/${e.request.status}/`);
            }
            console.log(e.request.status);
          });
      } else {
        alert("이미지를 등록해주세요.");
      }
    },
    putReview(data) {
      this.img = data;
      http
        .put(`/review/update/${this.$route.params.num}`, {
          num: this.item.num,
          title: this.title,
          content: this.content,
          nickName: this.getProfile,
          img: this.img,
          likeCnt: this.item.likeCnt,
          visit: this.item.visit,
          createdAt: this.item.createdAt,
          donationid: this.item.donationid
        })
        .then(({ data }) => {
          if (data == "success") {
            alert("성공적으로 수정 되었습니다.");
            this.$router.push(`/reviewDetail/${this.$route.params.num}`);
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    updateReview() {
      if (this.fileInfo != "") {
        var formData = new FormData();
        formData.append("img", this.fileInfo);
        http3
          .post(`/review/img`, formData)
          .then(({ data }) => {
            this.putReview(data);
          })
          .catch(e => {
            if (e.request.status === 404) {
              this.alertMsg = "등록 처리시 에러가 발생했습니다.";
              this.alert = true;
            } else {
              this.$router.push(`/apierror/${e.request.status}/`);
            }
            console.log(e.request.status);
          });
      } else {
        this.putReview(this.img);
      }
    },
    goBack() {
      this.$router.push(`/reviewList`);
    },
    onChangeImages(e) {
      const file = e.target.files[0];
      this.fileInfo = file;
      this.imageUrl = URL.createObjectURL(file);
    },
    onClickImageUpload() {
      this.$refs.imageInput.click();
    }
  }
};
</script>

<style>
</style>