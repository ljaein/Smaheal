<template>
  <v-container fluid class="col-md-11">
    <v-form>
      <div class="d-block d-md-none">
        <v-row class="mb-5 mt-3 d-flex justify-content-center"
        style="border:1px solid lightgray; ">
        <v-col align="center" class="m-0 my-auto pl-5">
          <span style="color:#356859; font-size:2rem;">{{ getUserID }}</span
          ><span style="font-size:1.5rem;"> 님의 회원등급은</span><br />
          <span
            style="font-size:1.5rem; vertical-align:middle; display:inline-flex;"
            ><v-icon style="color:#ffd700">mdi-crown-outline</v-icon>{{ grade
            }}<v-icon style="color:#ffd700" class="mr-2"
              >mdi-crown-outline</v-icon
            >입니다.</span
          >
        </v-col>
        </v-row>
        <v-row class="mb-5 mt-3 d-flex justify-content-center"
        style="border:1px solid lightgray; ">
          <v-col
            cols="3"
            class="my-auto"
            align="center"
            style="border-right:1px solid lightgray;"
          >
            <v-icon class="mb-2" style="font-size:3rem;"
              >mdi-account-circle-outline</v-icon
            ><br />
            <span style="font-size:1rem;">이름</span><br />
            <span style="font-size:1.5rem;">{{ name }}</span>
          </v-col>
          <v-col
          cols="3"
          class="my-auto"
          align="center"
          style="border-right:1px solid lightgray;"
        >
          <v-icon class="mb-2" style="font-size:3rem;"
            >mdi-card-account-details-outline</v-icon
          ><br />
          <span style="font-size:1rem;">닉네임</span><br />
          <span style="font-size:1.5rem;">{{ nickName }}</span>
        </v-col>
        <v-col
          cols="3"
          class="my-auto"
          align="center"
          style="border-right:1px solid lightgray;"
        >
          <v-icon class="mb-2" style="font-size:3rem;">mdi-cake-variant</v-icon
          ><br />
          <span style="font-size:1rem;">생일</span><br />
          <span style="font-size:1.5rem;">{{ mobileBirth }}</span>
        </v-col>
        <v-col cols="3" class="my-auto" align="center">
          <v-icon class="mb-2" style="font-size:3rem;"
            >mdi-hand-heart-outline</v-icon
          ><br />
          <span style="font-size:1rem;">기부횟수</span><br />
          <span style="font-size:1.5rem;">총 {{ smileCnt }}회</span>
        </v-col>
        </v-row>
      </div>
      <!-- 내 정보 -->
      <div class="d-none d-md-block">
      <v-row
        class="mb-5 mt-3 d-flex justify-content-center"
        style="border:1px solid lightgray; height:10rem;"
      >
        <v-col cols="4" align="left" class="m-0 my-auto pl-5">
          <span style="color:#356859; font-size:2rem;">{{ getUserID }}</span
          ><span style="font-size:1.5rem;"> 님의 회원등급은</span><br />
          <span
            style="font-size:1.5rem; vertical-align:middle; display:inline-flex;"
            ><v-icon style="color:#ffd700">mdi-crown-outline</v-icon>{{ grade
            }}<v-icon style="color:#ffd700" class="mr-2"
              >mdi-crown-outline</v-icon
            >입니다.</span
          >
        </v-col>
        <v-col
          cols="2"
          class="my-auto"
          align="center"
          style="border-right:1px solid lightgray;"
        >
          <v-icon class="mb-2" style="font-size:3rem;"
            >mdi-account-circle-outline</v-icon
          ><br />
          <span style="font-size:1rem;">이름</span><br />
          <span style="font-size:1.5rem;">{{ name }}</span>
        </v-col>
        <v-col
          cols="2"
          class="my-auto"
          align="center"
          style="border-right:1px solid lightgray;"
        >
          <v-icon class="mb-2" style="font-size:3rem;"
            >mdi-card-account-details-outline</v-icon
          ><br />
          <span style="font-size:1rem;">닉네임</span><br />
          <span style="font-size:1.5rem;">{{ nickName }}</span>
        </v-col>
        <v-col
          cols="2"
          class="my-auto"
          align="center"
          style="border-right:1px solid lightgray;"
        >
          <v-icon class="mb-2" style="font-size:3rem;">mdi-cake-variant</v-icon
          ><br />
          <span style="font-size:1rem;">생일</span><br />
          <span style="font-size:1.5rem;">{{ birth }}</span>
        </v-col>
        <v-col cols="2" class="my-auto" align="center">
          <v-icon class="mb-2" style="font-size:3rem;"
            >mdi-hand-heart-outline</v-icon
          ><br />
          <span style="font-size:1rem;">기부횟수</span><br />
          <span style="font-size:1.5rem;">총 {{ smileCnt }}회</span>
        </v-col>
      </v-row>
      </div>
      <v-row>
        <v-card style="width:100%">
          <v-tabs
            icons-and-text
            centered
            color="basil"
            class="basil--text"
            grow
          >
            <v-tab style="font-weight:bold; margin:0">
              내 웃음기부
              <!-- <v-icon>
                mdi-emoticon-excited-outline
              </v-icon> -->
            </v-tab>
            <v-tab style="font-weight:bold;">
              임시 저장
              <!-- <v-icon>
                mdi-content-save-edit
              </v-icon> -->
            </v-tab>
            <v-tab style="font-weight:bold;">
              내게 온 후기
              <!-- <v-icon>
                mdi-bullhorn-outline
              </v-icon> -->
            </v-tab>
            <v-tab style="font-weight:bold;">
              기부요청 현황
              <!-- <v-icon>
                mdi-image
              </v-icon> -->
            </v-tab>

            <v-tab-item>
              <MySmileComp />
            </v-tab-item>
            <v-tab-item>
              <TempComp />
            </v-tab-item>
            <v-tab-item>
              <ReviewToMeComp />
            </v-tab-item>
            <v-tab-item>
              <MyDonationComp />
            </v-tab-item>
          </v-tabs>
        </v-card>
      </v-row>
      <v-row justify="space-around">
        <v-col align="right" class="d-flex justify-content-end">
          <!-- <v-btn
            @click="modifyUser"
            color="#356859"
            class="mr-10"
            style="color:white;"
            >수정</v-btn
          > -->
          <v-btn
            outlined
            class="mr-3"
            @click="dialog = true"
            style="border-radius:20px; border:1.8px solid crimson; font-weight:bold;"
            >탈퇴하기</v-btn
          >
        </v-col>
      </v-row>
    </v-form>
    <v-row justify="center">
      <v-dialog v-model="dialog" persistent max-width="290">
        <v-card>
          <v-card-title>
            정말 탈퇴하시겠습니까?
          </v-card-title>
          <v-card-text></v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn style="color:crimson; font-weight:bold;" text @click="signOut()">
              탈퇴
            </v-btn>
            <v-btn style="font-weight:bold;" text @click="dialog = false">
              취소
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </v-container>
</template>

<script>
import http from "@/util/http-common";
import { mapGetters, mapState } from "vuex";
import { AUTH_LOGOUT } from "@/store/actions/auth";
import { USER_UPDATE } from "@/store/actions/user";
import TempComp from "@/components/account/TempComp.vue";
import MySmileComp from "@/components/account/MySmileComp.vue";
import MyDonationComp from "@/views/account/DonationList.vue";
import ReviewToMeComp from "@/components/account/ReviewToMeComp.vue";

export default {
  name: "MyPageComp",
  components: {
    TempComp,
    MySmileComp,
    MyDonationComp,
    ReviewToMeComp
  },
  data() {
    return {
      uid: "",
      name: "",
      log: "",
      nickName: "",
      birth: new Date().toISOString().substr(0, 10),
      smileCnt: 0,
      grade: "",
      dialog:false,
      mobileBirth: new Date().toISOString().substr(5, 5),
    };
  },
  created() {
    this.uid = this.getUserID;
    this.$store.dispatch(USER_UPDATE, this.getUserID).then(() => {});
    http
      .get(`/smile/getMySmileCnt/${this.getUserID}`)
      .then(res => {
        this.smileCnt = res.data;
        if (this.smileCnt >= 20) {
          this.grade = "포복절도";
        } else if (this.smileCnt >= 15) {
          this.grade = "박장대소";
        } else if (this.smileCnt >= 10) {
          this.grade = "함박웃음";
        } else if (this.smileCnt >= 5) {
          this.grade = "미소";
        } else {
          this.grade = "일반";
        }
      })
      .catch(err => {
        console.log(err);
      });
  },
  mounted() {
    this.name = this.getRealName;
    this.nickName = this.getProfile;
    this.birth = this.getFormatDate(this.getUserBirth);
    this.mobileBirth = this.getFormatDateMD(this.getUserBirth);
  },
  methods: {
    getFormatDateMD(regtime) {
      return new Date(regtime).toISOString().substr(5, 5);
    },
    getFormatDate(joinedAt) {
      return new Date(joinedAt).toISOString().substr(0, 10);
    },
    signOut() {
      this.dialog=true;
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
          } else {
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
.basil {
  background-color: #fffbe6 !important;
}
.basil--text {
  color: #356859 !important;
}
.user-info {
  font-size: 1.5rem;
  border: 3px solid #356859;
  width: 20%;
  height: 8rem;
}
</style>
