<template>
  <v-container class="pa-0">
    <v-sheet class="pa-6 mt-4 mb-5" dark>
      <v-icon class="mr-2">mdi-clipboard-alert-outline</v-icon>
      공지사항
    </v-sheet>
    <v-sheet dark class="pa-5 mb-5">
      <div class="headline pa-7">
        {{this.item.title}}
      </div>
      <div class="text-right pb-5">
        {{getFormatDate(this.item.createdAt)}}
      </div>
      <v-textarea readonly auto-grow solo flat
      :value="this.item.content">
      </v-textarea>
    </v-sheet>
    <v-row>
      <v-col>
        <v-btn class="mr-5" @click="goWrite()" outlined v-if="getUserID == 'admin'">
          수정
        </v-btn>
        <v-btn class="mr-5" @click="deleteNotice()" outlined v-if="getUserID == 'admin'">
          삭제
        </v-btn>
      </v-col>
      <v-col class="text-right">
        <v-btn @click="goBack()" outlined>
            목록으로 가기
        </v-btn>
      </v-col>
    </v-row>

    <v-dialog dark v-model="del" max-width="400">
      <v-card>
        <v-card-title class="headline">정말 삭제하시겠습니까?</v-card-title>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text @click="cancelDel()">취소</v-btn>

          <v-btn text @click="noticeDelHandler()">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import moment from "moment";
import http from "@/util/http-common.js";
import { mapGetters, mapState } from "vuex";

export default {
  name: "noticeDetailComp",
  props: {
      propItem: {type: Object}
  },
  data(){
   return {
       item: this.propItem,
       del: false
   };
  },
  methods: {
    goBack: function() {
      this.$router.push('/notice').catch(() => {})
    },
    getFormatDate: function(regtime) {
      return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
    },
    deleteNotice: function() {
      this.del = true
    },
    cancelDel: function() {
      this.del = false
    },
    noticeDelHandler: function() {
      http
      .delete(`/notice/delete/${this.item.noticeid}`)
      .then(({data}) => {
        if (data === "success") {
          this.del = false,
          this.$router.push('/notice')
        }
      })
    },
    goWrite: function() {
      this.$router.push(`/notice/modify/${this.item.noticeid}`)
    },
  },
  computed: {
    ...mapGetters([
      "isAuthenticated",
      "isProfileLoaded",
      "getProfile",
      "getRealName",
      "getUserNum",
      "getUserID",
      "getUserBirth",
    ]),
    ...mapState({
      authLoading: state => state.auth.status === "loading",
      uname: state => `${state.user.getProfile}`,
      userNum: state => `${state.user.getUserNum}`,
      userName: state => `${state.user.getRealName}`,
      userID: state => `${state.user.getUserID}`,
      userBirth: state => `${state.user.getUserBirth}`,
    }),
  },
};
</script>
