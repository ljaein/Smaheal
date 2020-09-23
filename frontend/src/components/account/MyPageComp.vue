<template>
  <div class="container">
    <h1 align="center" style="color:#356859;margin-top:50px;">MyPage</h1>
    <v-form>
      <v-container fluid>
        <v-row>
          <v-col class="px-7 mt-14 col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
            <v-row justify="space-around">
                <v-text-field class="mb-12" v-model="getUserID" outlined color="#356859" label="아이디" readonly></v-text-field>
            </v-row>
            <v-row justify="space-around">
                <v-text-field class="mb-12" v-model="name" outlined color="#356859" label="이름"></v-text-field>
            </v-row>
            <v-row justify="space-around">
                <v-text-field v-model="nickName" outlined color="#356859" label="별명"></v-text-field>
            </v-row>
          </v-col>
          <v-col class="px-7 col-xl-6 col-lg-6 col-md-6 col-sm-12 col-12">
            <v-row justify="space-around">
              <v-col cols="12">
                <p style="font-family: 'Sunflower'; font-weight: bold;">생일</p>
                <v-date-picker v-model="birth" color="#356859" width="100%"></v-date-picker>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-row justify="space-around">
          <v-col cols="12" md="3" align="center" class="d-flex justify-content-center">
            <v-btn @click="modifyUser" color="#356859" class="mr-10" style="color:white;">수정</v-btn>
            <v-btn @click="signOut" color="#356859" style="color:white;">탈퇴</v-btn>
          </v-col>
        </v-row>

        <!-- 유튜브 크롤링 임시 -->
        <div v-if="uid == 'admin'" class="col-4" style="margin:0 auto;text-align:center;">
          <v-text-field v-model="word" label="검색어" id="id"></v-text-field>
          <v-text-field v-model="age" label="나이" id="id"></v-text-field>
          <v-btn color="#356859" style="color:white;" @click="crawling">크롤링</v-btn>
        </div>
      </v-container>
    </v-form>
  </div>
</template>

<script>
import http from '@/util/http-common';
import { mapGetters, mapState } from 'vuex';
import { AUTH_LOGOUT } from '@/store/actions/auth';
import { USER_UPDATE } from '@/store/actions/user';

export default {
  name: 'MyPageComp',
  data() {
    return {
      word: '',
      age: '',
      uid: '',
      name: '',
      nickName: '',
      birth: new Date().toISOString().substr(0, 10),
    };
  },
  created() {
    this.uid = this.getUserID;
    this.$store.dispatch(USER_UPDATE, this.getUserID).then(() => {});
  },
  mounted() {
    this.name = this.getRealName;
    this.nickName = this.getProfile;
    this.birth = this.getFormatDate(this.getUserBirth);
  },
  methods: {
    crawling() {
      http.post(`/crawling/youtube?search=${this.word}&age=${this.age}`)
      .then(res => {
        console.log(res.data);
      })
      .catch(err => {
        console.log(err);
      })
    },
    getFormatDate(joinedAt) {
      return new Date(joinedAt).toISOString().substr(0, 10);
    },
    signOut() {
      http
        .delete(`/user/delete/${this.getUserNum}`)
        .then(() => {
          alert('탈퇴가 완료되었습니다.');
          this.logout();
        })
        .catch((e) => {
          if (e.request.status === 404) {
            alert('탈퇴 처리시 에러가 발생했습니다.');
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
      this.$router.push('/');
    },
    modifyUser() {
      http
        .put(`/user/${this.getUserNum}`, {
          name: this.name,
          nickName: this.nickName,
          birth: this.birth,
        })
        .then(({ data }) => {
          if (data === 'success') {
            alert('수정이 완료되었습니다.');
            this.$router.push('/');
            this.$router.go();
          } else {
            alert('수정 처리시 문제가 발생했습니다.');
          }
        })
        .catch((e) => {
          if (e.request.status === 404) {
            alert('수정 처리시 에러가 발생했습니다.');
          } else {
            this.$emit('closeLoginModal');
            this.$router.push(`/apierror/${e.request.status}/`);
          }
          console.log(e.request.status);
        });
    },
  },
  computed: {
    ...mapGetters(['isAuthenticated', 'isProfileLoaded', 'getProfile', 'getRealName', 'getUserNum', 'getUserID', 'getUserBirth']),
    ...mapState({
      authLoading: (state) => state.auth.status === 'loading',
      uname: (state) => `${state.user.getProfile}`,
      userNum: (state) => `${state.user.getUserNum}`,
      userName: (state) => `${state.user.getRealName}`,
      userID: (state) => `${state.user.getUserID}`,
      userBirth: (state) => `${state.user.getUserBirth}`,
    }),
  },
};
</script>

<style></style>
