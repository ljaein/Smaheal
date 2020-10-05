<template>
  <v-container id="inspire" class="">
    <v-row class="pa-0 col-9" style="margin:0 auto;" no-gutters>
      <v-col class="py-15">
        <v-sheet class="text-left text-h2 amber--text mb-7">
          SMAHEAL
        </v-sheet>
        <v-col class="pr-10 pl-10">
          <v-form ref="form" v-model="valid">
            <v-row justify="center">
              <v-col class="text-left pt-8 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-12">
                * 아이디
              </v-col>
              <v-col class="col-xl-8 col-lg-8 col-md-8 col-sm-8 col-12">
                <v-text-field
                  v-model="id"
                  :counter="15"
                  :rules="idRules"
                  color="#356859"
                  placeholder="사용할 아이디를 입력해주세요."
                  required
                  clearable
                ></v-text-field>
              </v-col>
              <v-col class="text-right pt-6 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-12">
                <v-btn class="green-mbtn" outlined @click="idCheck()">중복체크</v-btn>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col class="text-left pt-8 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-12">
                * 비밀번호
              </v-col>
              <v-col class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
                <v-text-field
                  v-model="password1"
                  type="password"
                  :counter="20"
                  :rules="passwordRules"
                  placeholder="비밀번호(영문자, 숫자 포함 8자 이상 입력해주세요.)"
                  required
                  clearable
                  color="#356859"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col class="text-left pt-8 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-12">
                * 비밀번호 재입력
              </v-col>
              <v-col class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
                <v-text-field
                  v-model="password2"
                  type="password"
                  :counter="20"
                  :rules="passwordRules"
                  placeholder="비밀번호를 재입력해주세요."
                  required
                  clearable
                  color="#356859"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col class="text-left pt-8 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-12">
                * 이름
              </v-col>
              <v-col class="col-xl-10 col-lg-10 col-md-10 col-sm-10 col-12">
                <v-text-field
                  v-model="name"
                  :counter="15"
                  :rules="nameRules"
                  placeholder="이름을 입력해주세요."
                  color="#356859"
                  required
                  clearable
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row justify="center">
              <v-col class="text-left pt-8 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-12">
                * 별명
              </v-col>
              <v-col class="col-xl-8 col-lg-8 col-md-8 col-sm-8 col-12">
                <v-text-field
                  v-model="nickName"
                  :counter="10"
                  :rules="nickNameRules"
                  placeholder="사용할 별명을 입력해주세요.(10자 내외)"
                  color="#356859"
                  required
                  clearable
                ></v-text-field>
              </v-col>
              <v-col class="text-right pt-6 col-xl-2 col-lg-2 col-md-2 col-sm-2 col-12">
                <v-btn class="green-mbtn" outlined @click="nickCheck()">중복체크</v-btn>
              </v-col>
            </v-row>
            <v-row justify="center">
              <v-col class="text-left pt-8 col-12">
                * 달력에서 생일을 선택하세요.
              </v-col>
            </v-row>
            <v-col class="col-12">
              <v-date-picker v-model="birth" color="#356859"></v-date-picker>
            </v-col>

            <v-btn class="pa-5 col-12" :disabled="!valid" color="#356859" dark width="100%" @click="validate">회원가입</v-btn>
          </v-form>
          <div style="text-align: right">
            <v-btn class="mt-5 green-mbtn" @click="goLogin()">돌아가기</v-btn>
          </div>
        </v-col>
      </v-col>
    </v-row>
    <!-- alert -->
    <v-snackbar v-model="idFlag" top right flat color="error" :timeout="2000">
      <p class="snackText">존재하는 아이디입니다.</p>
    </v-snackbar>
    <v-snackbar v-model="nickFlag" top right flat color="error" :timeout="2000">
      <p class="snackText">존재하는 별명입니다.</p>
    </v-snackbar>
    <v-snackbar v-model="idOk" top right flat color="#356859" :timeout="2000">
      <p class="snackText">사용 가능한 아이디입니다.</p>
    </v-snackbar>
    <v-snackbar v-model="nickOk" top right flat color="#356859" :timeout="2000">
      <p class="snackText">사용 가능한 별명입니다.</p>
    </v-snackbar>
    <v-snackbar v-model="noidOk" top right flat color="error" :timeout="2000">
      <p class="snackText">아이디 중복확인을 해주세요.</p>
    </v-snackbar>
    <v-snackbar v-model="nonickOk" top right flat color="error" :timeout="2000">
      <p class="snackText">별명 중복확인을 해주세요.</p>
    </v-snackbar>
    <v-snackbar v-model="noOk" top right flat color="error" :timeout="2000">
      <p class="snackText">아이디와 별명 중복확인을 해주세요.</p>
    </v-snackbar>
    <v-snackbar v-model="pwdNo" top right flat color="error" :timeout="2000">
      <p class="snackText">비밀번호가 다릅니다.</p>
    </v-snackbar>
    <v-snackbar v-model="signOk" top right flat color="#356859" :timeout="2000">
      <p class="snackText">가입이 완료되었습니다.</p>
    </v-snackbar>
  </v-container>
</template>

<script>
import http2 from '@/util/http-common2.js';
import http from '@/util/http-common.js';

export default {
  name: 'SignUp',
  data() {
    return {
      id: '',
      name: '',
      password1: '',
      password2: '',
      nickName: '',
      birth: new Date().toISOString().substr(0, 10),
      valid: false,
      nameRules: [(v) => !!v || '이름을 입력해주세요', (v) => (v && v.length <= 15) || '이름은 최대 15글자까지만 사용할 수 있습니다'],
      idRules: [(v) => !!v || '아이디를 입력해주세요', (v) => (v && v.length <= 15) || '아이디는 최대 15글자까지만 사용할 수 있습니다'],
      nickNameRules: [(v) => !!v || '별명을 입력해주세요', (v) => (v && v.length <= 10) || '별명은 최대 10글자까지만 사용할 수 있습니다'],
      passwordRules: [
        (v) => !!v || '비밀번호를 입력해주세요',
        (v) => (v && v.length <= 20) || '비밀번호는 최대 20글자까지만 사용할 수 있습니다',
      ],
      idFlag: false,
      nickFlag: false,
      idOk: false,
      nickOk: false,
      noidOk: false,
      nonickOk: false,
      noOk: false,
      pwdNo: false,
      idCk: false,
      nicCk: false,
      signOk: false,
    };
  },
  methods: {
    validate() {
      this.$refs.form.validate();
      if (this.password1 == this.password2) {
        if(this.idCk == false && this.nicCk == true) {
          this.noidOk = true;
        } else if (this.nicCk == false && this.idCk == true) {
          this.nonickOk = true;
        } else if(this.nicCk == false && this.idCk == false) {
          this.noOk = true;
        } else {
          this.signUpRequest();
        }
      } else {
        this.pwdNo = true;
      }
    },
    signUpRequest() {
      http2
        .post('/signup', {
          userId: this.id,
          name: this.name,
          password: this.password1,
          nickName: this.nickName,
          birth: this.birth,
        })
        .then((response) => {
          if (response.data.success == true || response.data.success == 'true') {
            this.signOk = true;
            setTimeout(() => {
              this.$router.push('/');
            }, 1500);
          }
        })
        .catch((e) => {
          if (e.request.status === 404) {
            this.alertMsg = '회원가입에 실패하였습니다.';
            this.alert = true;
          } else {
            this.visablelogin = false;
            this.submitted = false;
            this.newCustomer();
            this.$router.push(`/apierror/${e.request.status}/`);
          }
          console.log(e.request.status);
        });
    },
    goLogin: function() {
      this.$router.push('/login');
    },
    idCheck() {
      http
        .get(`user/checkId/${this.id}`)
        .then(res => {
          if(res.data != null) {
            this.idFlag = true;
            this.id = '';
          } else {
            this.idOk = true;
            this.idCk = true;
          }
        })
    },
    nickCheck() {
      http
        .get(`user/checkNick/${this.nickName}`)
        .then(res => {
          if(res.data != null) {
            this.nickFlag = true;
            this.nickName = '';
          } else {
            this.nickOk = true;
            this.nicCk = true;
          }
        })
    },
  },
};
</script>

<style scoped>
.snackText {
  margin-bottom:0;
  font-weight:bold;
  font-size:1rem;
  word-spacing:2px;
  letter-spacing:2px;
}
</style>
