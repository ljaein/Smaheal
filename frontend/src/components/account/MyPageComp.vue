<template>
  <v-container fluid class="col-md-11">
    <p align="left" class="m-0">
      <span style="color:#356859; font-size:2.5rem;">{{ getUserID }}</span
      ><span style="font-size:1.5rem;"> 님의 마이페이지</span>
    </p>
    <v-form>
      <!-- 내 정보 -->
      <v-row>
        <v-col>
          <v-row class="d-flex justify-content-between">
            <v-text-field
              class="mr-5"
              v-model="name"
              outlined
              color="#356859"
              label="이름"
            ></v-text-field>
            <v-text-field
              class="mr-5"
              v-model="nickName"
              outlined
              color="#356859"
              label="닉네임"
            ></v-text-field>
            <v-text-field
              v-model="birth"
              outlined
              color="#356859"
              label="생일"
            ></v-text-field>
          </v-row>
          <v-row> </v-row>
        </v-col>
      </v-row>
      <!-- 글 정보 -->
      <v-row>
        <v-card>
          <v-tabs vertical icons-and-text centered>
            <v-tab style="font-weight:bold; margin:0">
              기부 현황
              <v-icon>
                mdi-account
              </v-icon>
            </v-tab>
            <v-tab style="font-weight:bold;">
              임시 저장
              <v-icon>
                mdi-lock
              </v-icon>
            </v-tab>
            <v-tab style="font-weight:bold;">
              내게 온 후기
              <v-icon>
                mdi-access-point
              </v-icon>
            </v-tab>

            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <p>
                    Morbi nec metus. Suspendisse faucibus, nunc et pellentesque
                    egestas, lacus ante convallis tellus, vitae iaculis lacus
                    elit id tortor. Sed mollis, eros et ultrices tempus, mauris
                    ipsum aliquam libero, non adipiscing dolor urna a orci.
                    Curabitur ligula sapien, tincidunt non, euismod vitae,
                    posuere imperdiet, leo. Nunc sed turpis.
                  </p>

                  <p>
                    Suspendisse feugiat. Suspendisse faucibus, nunc et
                    pellentesque egestas, lacus ante convallis tellus, vitae
                    iaculis lacus elit id tortor. Proin viverra, ligula sit amet
                    ultrices semper, ligula arcu tristique sapien, a accumsan
                    nisi mauris ac eros. In hac habitasse platea dictumst. Fusce
                    ac felis sit amet ligula pharetra condimentum.
                  </p>

                  <p>
                    Sed consequat, leo eget bibendum sodales, augue velit cursus
                    nunc, quis gravida magna mi a libero. Nam commodo suscipit
                    quam. In consectetuer turpis ut velit. Sed cursus turpis
                    vitae tortor. Aliquam eu nunc.
                  </p>

                  <p>
                    Etiam ut purus mattis mauris sodales aliquam. Ut varius
                    tincidunt libero. Aenean viverra rhoncus pede. Duis leo.
                    Fusce fermentum odio nec arcu.
                  </p>

                  <p class="mb-0">
                    Donec venenatis vulputate lorem. Aenean viverra rhoncus
                    pede. In dui magna, posuere eget, vestibulum et, tempor
                    auctor, justo. Fusce commodo aliquam arcu. Suspendisse enim
                    turpis, dictum sed, iaculis a, condimentum nec, nisi.
                  </p>
                </v-card-text>
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card flat>
              <TempComp />
              </v-card>
            </v-tab-item>
            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <p>
                    Fusce a quam. Phasellus nec sem in justo pellentesque
                    facilisis. Nam eget dui. Proin viverra, ligula sit amet
                    ultrices semper, ligula arcu tristique sapien, a accumsan
                    nisi mauris ac eros. In dui magna, posuere eget, vestibulum
                    et, tempor auctor, justo.
                  </p>

                  <p class="mb-0">
                    Cras sagittis. Phasellus nec sem in justo pellentesque
                    facilisis. Proin sapien ipsum, porta a, auctor quis, euismod
                    ut, mi. Donec quam felis, ultricies nec, pellentesque eu,
                    pretium quis, sem. Nam at tortor in tellus interdum
                    sagittis.
                  </p>
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </v-card>
      </v-row>
      <v-row justify="space-around">
        <v-col
          cols="12"
          md="3"
          align="center"
          class="d-flex justify-content-center"
        >
          <v-btn
            @click="modifyUser"
            color="#356859"
            class="mr-10"
            style="color:white;"
            >수정</v-btn
          >
          <v-btn @click="signOut" color="#356859" style="color:white;"
            >탈퇴</v-btn
          >
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
import http from "@/util/http-common";
import { mapGetters, mapState } from "vuex";
import { AUTH_LOGOUT } from "@/store/actions/auth";
import { USER_UPDATE } from "@/store/actions/user";
import TempComp from "@/components/account/TempComp.vue";

export default {
  name: "MyPageComp",
  components: {
    TempComp
  },
  data() {
    return {
      uid: "",
      name: "",
      log: "",
      nickName: "",
      birth: new Date().toISOString().substr(0, 10)
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

<style></style>
