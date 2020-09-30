<template>
  <v-app>
    <v-app-bar app color="white" height="100">
      <!-- <v-avatar class="mr-3" color="grey lighten-5" size="70">
        <v-img
          contain
          max-height="70%"
          src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
        ></v-img>
      </v-avatar> -->

      <v-btn @click="goHome()" class="headline" color="amber accent-4" text>SmaHeal</v-btn>

      <v-spacer></v-spacer>

      <!-- <v-btn @click="goDonationBoardDetail()" text>
        <span class="mr-2">ai_video test</span>
      </v-btn> -->

      <v-btn @click="goDonationList()" text>
        <span class="mr-2 font-weight-bold">기부 게시판</span>
      </v-btn>

      <v-btn @click="goReviewList()" text>
        <span class="mr-2 font-weight-bold">후기 게시판</span>
      </v-btn>

      <v-btn @click="goNotice()" text>
        <span class="mr-2 font-weight-bold">공지사항</span>
      </v-btn>

      <v-btn v-if="getProfile!='관리자'" @click="goMyPage" text>
        <span class="mr-2 font-weight-bold">마이페이지</span>
      </v-btn>

      <v-btn v-if="getProfile=='관리자'" @click="goAdminPage" text>
        <span class="mr-2 font-weight-bold">관리자페이지</span>
      </v-btn>

      <v-btn v-if="getProfile" @click="logout" text>
        <span class="mr-2 font-weight-bold">LOGOUT</span>
      </v-btn>

      <v-btn v-else @click="goLogin" text>
        <span class="mr-2 font-weight-bold">LOGIN</span>
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>

    <v-snackbar
      style="font-family: 'Nanum Gothic';"
      shaped
      color="#356859"
      elevation="24"
      v-model="loginSuccess"
      timeout="3000"
    >
      <v-icon color="white" class="mr-1">mdi-emoticon-excited-outline</v-icon>
      {{ getProfile + "님 반갑습니다." }}
      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="loginSuccess = false">Close</v-btn>
      </template>
    </v-snackbar>

    <v-snackbar
      style="font-family: 'Nanum Gothic';"
      shaped
      color="#356859"
      elevation="24"
      v-model="logoutSuccess"
      timeout="3000"
    >
      <v-icon color="white" class="mr-1">mdi-check-bold</v-icon>정상적으로 로그아웃 되었습니다.
      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="logoutSuccess = false">Close</v-btn>
      </template>
    </v-snackbar>
  </v-app>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import { AUTH_LOGOUT } from "./store/actions/auth";
import { USER_UPDATE } from "@/store/actions/user";

export default {
  name: "App",
  components: {
    
  },
  data(){
    return{
      logoutSuccess: false,
      loginSuccess: false,
    }
  },
  created(){
    this.$store.dispatch(USER_UPDATE, this.getUserID).then(() => {});
  },
  methods: {
    goDonationBoardDetail: function() {
      // 경로 중복 문제가 콘솔에 안 뜨게 하기 위해서 catch 이용
      this.$router.push("/donationDetail").catch(() => {});
    },
    goLogin() {
      this.$router.push('/login').catch(() => {}); 
    },
    goNotice() {
      this.$router.push('/notice').catch(() => {})
    },
    goHome() {
      this.$router.push('/').catch(() => {})
      scroll(0,0)
    },
    goDonationList(){
      this.$router.push('/donationList')
    },
    logout: function() {
      this.$store.dispatch(AUTH_LOGOUT).then(() => {
        this.drawer = false;
      });
      this.$router.push("/");
    },
    goReviewList() {
      this.$router.push('/reviewList').catch(() => {}); 
    },
    goMyPage(){
      this.$router.push('/myPage').catch(() => {}); 
    },
    goAdminPage(){
      this.$router.push('/adminPage').catch(() => {}); 
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
  watch: {
    getProfile: function() {
      if (this.getProfile == "") {
        this.logoutSuccess = true;
      } else {
        this.loginSuccess = true;
      }
    },

  }
};
</script>
<style>
#app{
  font-family: 'Nanum Gothic', sans-serif;
  font-weight:bold;
}
html{
  scroll-behavior: smooth;
}
</style>