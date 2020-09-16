<template>
  <div>
    <v-form>
      <v-container>
        <v-row>
          <v-col cols="12" md="6">
            <v-text-field v-model="title" label="제목" required></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <input ref="imageInput" type="file" hidden @change="onChangeImages" />
          <v-btn
            class="mt-2"
            style="font-family: 'Nanum Gothic';"
            type="button"
            @click="onClickImageUpload"
          >이미지 업로드</v-btn>
        </v-row>
        <v-row>
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
    <v-btn v-if="isModify" @click="updateReview">수정</v-btn>
    <v-btn v-else @click="writeReview">등록</v-btn>
    <v-btn @click="goBack">뒤로가기</v-btn>
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
          img: data
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
      if (this.fileInfo != null) {
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
          createdAt: this.item.createdAt
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
      if (this.fileInfo != null) {
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
    },

    modifyHandler: function() {
      if (this.fileInfo != null) {
        var formData = new FormData();
        formData.append("img", this.fileInfo);
        http3
          .post(`/user/img`, formData)
          .then(({ data }) => {
            http
              .put(`/users/${this.getUserNum}`, {
                name: this.name,
                nickname: this.nickName,
                intro: this.intro,
                imagesrc: data
              })
              .then(({ data1 }) => {
                let msg = "수정 처리시 문제가 발생했습니다.";
                if (data1 === "success") {
                  msg = "수정이 완료되었습니다.";
                  this.$router.go();
                }
                this.alertMsg = msg;
                this.alert = true;
              })
              .catch(e => {
                if (e.request.status === 404) {
                  this.alertMsg = "탈퇴 처리시 에러가 발생했습니다.";
                  this.alert = true;
                } else {
                  this.$emit("closeLoginModal");
                  this.$router.push(`/apierror/${e.request.status}/`);
                }
                console.log(e.request.status);
              });
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
      }
    }
  }
};
</script>

<style>
</style>