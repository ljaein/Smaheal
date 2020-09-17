<template>
  <div>
    <h1 align="center"> MyPage </h1>
    <v-form>
      <v-container fluid>
        <v-row justify="space-around">
          <v-col cols="12" md="4">
            <v-text-field
              v-model="getUserID"
              filled
              color="blue-grey lighten-2"
              label="아이디"
              readonly
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row justify="space-around">
          <v-col cols="12" md="4">
            <v-text-field v-model="name" filled color="blue-grey lighten-2" label="이름"></v-text-field>
          </v-col>
        </v-row>
        <v-row justify="space-around">
          <v-col cols="12" md="4">
            <v-text-field v-model="nickName" filled color="blue-grey lighten-2" label="별명"></v-text-field>
          </v-col>
        </v-row>
        <v-row justify="space-around">
          <v-col cols="12" md="4">
            <p style="font-family: 'Sunflower'; font-weight: bold;">생일</p>
            <v-date-picker v-model="birth" width="100%"></v-date-picker>
          </v-col>
        </v-row>
        <v-row justify="space-around">
          <v-col cols="12" md="2" align="center">
            <v-btn @click="modifyUser" outlined>수정</v-btn>
            <v-btn @click="signOut" outlined>탈퇴</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapGetters, mapState } from "vuex";
import { AUTH_LOGOUT } from "@/store/actions/auth";
import { USER_UPDATE } from "@/store/actions/user";

export default {
  name: "MyPageComp",
  data() {
    return {
      name: "",
      nickName: "",
      birth: new Date().toISOString().substr(0, 10),
    };
  },
  created() {
    this.$store.dispatch(USER_UPDATE, this.getUserID).then(() => {});
    
  },
  mounted(){
    this.name = this.getRealName;
    this.nickName = this.getProfile;
    this.birth = this.getFormatDate(this.getUserBirth);
  },
  methods: {
    getFormatDate(joinedAt) {
      return new Date(joinedAt).toISOString().substr(0, 10);
    },
    signOut() {
      http
        .delete(`/user/delete/${this.getUserNum}`)
        .then(() => {
          alert("탈퇴가 완료되었습니다.");
          this.logout();
        })
        .catch(e => {
          if (e.request.status === 404) {
            alert("탈퇴 처리시 에러가 발생했습니다.");
            this.alert = true;
          } else {
            this.$router.push(`/apierror/${e.request.status}/`);
          }
          console.log(e.request.status);
        });
    },
    logout: function() {
      this.$store.dispatch(AUTH_LOGOUT).then(() => {
        this.drawer = false;
      });
      this.$router.push("/");
    },
    modifyUser() {
      http
        .put(`/user/${this.getUserNum}`, {
          name: this.name,
          nickName: this.nickName,
          birth: this.birth
        })
        .then(({ data }) => {
          if (data === "success") {
            alert("수정이 완료되었습니다.");
            this.$router.push("/");
            this.$router.go();
          }else{
            alert("수정 처리시 문제가 발생했습니다.");
          }
        })
        .catch(e => {
          if (e.request.status === 404) {
            alert("수정 처리시 에러가 발생했습니다.");
          } else {
            this.$emit("closeLoginModal");
            this.$router.push(`/apierror/${e.request.status}/`);
          }
          console.log(e.request.status);
        });
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
  }
};
</script>

<style>
</style>