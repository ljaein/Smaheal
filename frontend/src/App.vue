<template>
  <v-app>
    <v-app-bar app class="d-block d-md-none"
      ><v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-btn @click="goHome()" class="headline" color="amber accent-4" text align-center
        >SmaHeal</v-btn
      >
    </v-app-bar>
    
    <v-navigation-drawer v-model="drawer" absolute temporary>
      <div v-if="getProfile" class="text-center">
        <v-btn block dark depressed color="amber" @click="logout">
          LOGOUT
        </v-btn>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="title">
              {{ getUserID }} 님
            </v-list-item-title>
            <v-list-item-subtitle>
              환영합니다!
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </div>
      <div v-else class="text-center">
        <v-btn block dark depressed color="amber" @click="goLogin">
          LOGIN
        </v-btn>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="title">
              로그인을 해주세요
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </div>
      <v-list nav dense rounded>
        <v-list-item-group
          v-model="group"
          active-class="amber--text text--accent-4"
        >
          <v-list-item @click="goHome()">
            <v-list-item-icon>
              <v-icon>mdi-home</v-icon>
            </v-list-item-icon>
            <v-list-item-title>메인 화면</v-list-item-title>
          </v-list-item>

          <v-list-item @click="goDonationList()">
            <v-list-item-icon>
              <v-icon>mdi-clipboard-text</v-icon>
            </v-list-item-icon>
            <v-list-item-title>기부 게시판</v-list-item-title>
          </v-list-item>

          <v-list-item @click="goReviewList()">
            <v-list-item-icon>
              <v-icon>mdi-message-draw</v-icon>
            </v-list-item-icon>
            <v-list-item-title>후기 게시판</v-list-item-title>
          </v-list-item>

          <v-list-item @click="goNotice()">
            <v-list-item-icon>
              <v-icon>mdi-clipboard-alert</v-icon>
            </v-list-item-icon>
            <v-list-item-title>공지사항</v-list-item-title>
          </v-list-item>

          <v-list-item v-if="getProfile != '관리자'" @click="goMyPage">
            <v-list-item-icon>
              <v-icon>mdi-clipboard-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>마이페이지</v-list-item-title>
          </v-list-item>

          <v-list-item v-if="getProfile == '관리자'" @click="goAdminPage">
            <v-list-item-icon>
              <v-icon>mdi-clipboard-account</v-icon>
            </v-list-item-icon>
            <v-list-item-title>마이페이지</v-list-item-title>
          </v-list-item>

          <v-list-item v-if="isSmileKing" @click="goAward()">
            <v-list-item-icon>
              <v-icon>mdi-seal</v-icon>
            </v-list-item-icon>
            <v-list-item-title>상장 보기</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app color="white" height="100" class="d-none d-md-block">
      <v-btn @click="goHome()" class="headline" color="amber accent-4" text
        >SmaHeal</v-btn
      >

      <v-spacer></v-spacer>

      <!-- <v-btn @click="goDonationBoardDetail()" text>
        <span class="mr-2">ai_video test</span>
      </v-btn> -->

      <v-btn v-if="isSmileKing" @click="goAward()" text>
        <span class="mr-2 font-weight-bold">상장 보기</span>
      </v-btn>

      <v-btn @click="goDonationList()" text>
        <span class="mr-2 font-weight-bold">기부 게시판</span>
      </v-btn>

      <v-btn @click="goReviewList()" text>
        <span class="mr-2 font-weight-bold">후기 게시판</span>
      </v-btn>

      <v-btn @click="goNotice()" text>
        <span class="mr-2 font-weight-bold">공지사항</span>
      </v-btn>

      <v-btn v-if="getProfile != '관리자'" @click="goMyPage" text>
        <span class="mr-2 font-weight-bold">마이페이지</span>
      </v-btn>

      <v-btn v-if="getProfile == '관리자'" @click="goAdminPage" text>
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
        <v-btn color="white" text v-bind="attrs" @click="loginSuccess = false"
          >Close</v-btn
        >
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
      <v-icon color="white" class="mr-1">mdi-check-bold</v-icon>정상적으로
      로그아웃 되었습니다.
      <template v-slot:action="{ attrs }">
        <v-btn color="white" text v-bind="attrs" @click="logoutSuccess = false"
          >Close</v-btn
        >
      </template>
    </v-snackbar>

    <v-tooltip top>
      <template v-slot:activator="{ on, attrs }">
        <v-fab-transition>
          <v-btn
            fab
            large
            bottom
            dark
            right
            class="v-btn--example orange"
            fixed
            v-on="on"
            v-bind="attrs"
            @click="goSearchTemplate()"
          >
            <v-icon>mdi-table-search</v-icon>
          </v-btn>
        </v-fab-transition>
      </template>
      <span>기부 사진 템플릿 찾기</span>
    </v-tooltip>
    <v-footer class="justify-center" color="#292929" height="100">
      <div
        class="title font-weight-light grey--text text--lighten-1 text-center"
      >
        &copy; {{ new Date().getFullYear() }} — SmaHeal — Made with 💜 by 미찾사
      </div>
    </v-footer>
  </v-app>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import { AUTH_LOGOUT } from "./store/actions/auth";
import { USER_UPDATE } from "@/store/actions/user";
import http from "@/util/http-common.js";

export default {
  name: "App",
  components: {},
  data() {
    return {
      logoutSuccess: false,
      loginSuccess: false,
      isSmileKing: false,
      drawer: false,
      group: null
    };
  },
  created() {
    this.$store.dispatch(USER_UPDATE, this.getUserID).then(() => {});
    http
      .get(`/smile/smileKing`)
      .then(res => {
        const smileKing = res.data;
        for (const smile of smileKing) {
          if (smile.userId == this.getUserID) {
            this.isSmileKing = true;
          }
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  methods: {
    goDonationBoardDetail: function() {
      // 경로 중복 문제가 콘솔에 안 뜨게 하기 위해서 catch 이용
      this.$router.push("/donationDetail").catch(() => {});
    },
    goLogin() {
      this.$router.push("/login").catch(() => {});
    },
    goNotice() {
      this.$router.push("/notice").catch(() => {});
    },
    goHome() {
      this.$router.push("/").catch(() => {});
      scroll(0, 0);
    },
    goDonationList() {
      this.$router.push("/donationList").catch(() => {});
    },
    logout: function() {
      this.$store.dispatch(AUTH_LOGOUT).then(() => {
        this.drawer = false;
      });
      this.$router.push("/").catch(() => {});
    },
    goReviewList() {
      this.$router.push("/reviewList").catch(() => {});
    },
    goMyPage() {
      this.$router.push("/myPage").catch(() => {});
    },
    goAdminPage() {
      this.$router.push("/adminPage").catch(() => {});
    },
    goSearchTemplate() {
      this.$router.push("/template").catch(() => {});
    },
    goAward() {
      this.$router.push("/award").catch(() => {});
    }
  },
  computed: {
    ...mapGetters([
      "isAuthenticated",
      "isProfileLoaded",
      "getProfile",
      "getRealName",
      "getUserNum",
      "getUserID",
      "getUserBirth"
    ]),
    ...mapState({
      authLoading: state => state.auth.status === "loading",
      uname: state => `${state.user.getProfile}`,
      userNum: state => `${state.user.getUserNum}`,
      userName: state => `${state.user.getRealName}`,
      userID: state => `${state.user.getUserID}`,
      userBirth: state => `${state.user.getUserBirth}`
    })
  },
  watch: {
    getProfile: function() {
      if (this.getProfile == "") {
        this.logoutSuccess = true;
      } else {
        this.loginSuccess = true;
      }
    },
    group() {
      this.drawer = false;
    }
  }
};
</script>
<style>
#app {
  font-family: "Nanum Gothic", sans-serif;
  font-weight: bold;
}
html {
  scroll-behavior: smooth;
}
</style>