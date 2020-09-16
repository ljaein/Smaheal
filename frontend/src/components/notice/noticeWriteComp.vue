<template>
  <v-container class="pa-0">
    <v-sheet class="pa-6 mt-4 mb-5" dark>
      <v-icon class="mr-2">mdi-clipboard-edit-outline</v-icon>
      공지사항 작성페이지
    </v-sheet>
    <v-sheet dark class="pa-5 mb-5">
        <v-text-field
        placeholder="제목을 입력하세요."
        color="white"
        v-model="title"
        ref="title"
        class="headline"
        prepend-icon="mdi-lightbulb-on"
        clearable
        :counter="20"
        :rules="titleRules"
        />
        <v-textarea
        placeholder="공지사항 내용을 입력하세요."
        color="white"
        v-model="content"
        ref="content"
        outlined
        clearable
        height="300"
        :counter="1000"
        :rules="contentRules"
        />
    </v-sheet>
    <v-row>
        <v-col>
            <v-btn @click="noticeTempRegist()" outlined>
                임시저장
            </v-btn>
        </v-col>
        <v-col class="text-right">
            <v-btn class="mr-5" @click="goBack()" outlined>
                취소
            </v-btn>
            <v-btn @click="noticeRegist()" outlined>
                등록
            </v-btn>
        </v-col>
    </v-row>

    <v-dialog dark v-model="regist" max-width="400">
      <v-card>
        <v-card-title class="headline">공지사항을 등록하겠습니까?</v-card-title>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text @click="cancelRegist()">취소</v-btn>

          <v-btn text @click="noticeRegistHandler()">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import http from "@/util/http-common.js";

export default {
  name: "noticeWriteComp",
  data(){
   return {
       title: "",
       content: "",
       regist: false,
       alertMsg: "",
       alert: false,
       titleRules: [
           v => (v && v.length <= 20) || "제목은 20글자 이내만 가능합니다."
       ],
       contentRules: [
           v => (v && v.length <= 1000) || "내용은 1000글자 이내만 가능합니다."
       ],
   };
  },
  methods: {
      noticeRegist: function() {
          let err = true;
          let msg = "";

          !this.title && ((msg = "제목을 입력해주세요"),
          (err = false), this.$refs.title.focus());
          !this.content && ((msg = "내용을 입력해주세요"),
          (err = false), this.$refs.content.focus());

          if (!err) {
              this.alertMsg = msg;
              this.alert = true;
          } else {
              this.regist = true;
          }
      },
      noticeRegistHandler: function() {
          http.post("/notice/write", {
              title: this.title,
              content: this.content,
          })
          .then(
              this.regist = false,
              this.$router.push('/notice')
          )
          .catch(e => {
              this.alertMsg = "등록 처리시 에러가 발생했습니다.";
              this.alert = true;
              console.log(e)
          })
      },
      noticeTempRegist: function() {

      },
      goBack: function() {
          this.$router.push('/notice')
      },
      cancelRegist: function() {
          this.regist = false
      }
  },
};
</script>
