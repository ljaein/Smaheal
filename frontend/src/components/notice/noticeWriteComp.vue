<template>
  <v-container>
    <v-sheet color="orange lighten-2">공지사항 작성페이지</v-sheet>
    <v-sheet outlined class="pa-5 mb-5">
        <v-text-field
        placeholder="제목을 입력하세요."
        color="orange"
        v-model="title"
        class="headline"
        />
        <v-textarea
        placeholder="공지사항 내용을 입력하세요."
        color="orange"
        v-model="content"
        />
    </v-sheet>
    <v-btn @click="noticeTempRegist()" text>
        임시저장
    </v-btn>
    <v-btn @click="goBack()" text>
        취소
    </v-btn>
    <v-btn @click="noticeRegist()" text>
        등록
    </v-btn>

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
   };
  },
  methods: {
      noticeRegist: function() {
          this.regist = true
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
