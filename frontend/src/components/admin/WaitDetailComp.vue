<template>
  <v-container fluid
    class="modal fade mt-5 pl-15 pr-15"
    id="waitDetail"
    tabindex="-1"
    aria-labelledby="exampleModalLabel"
    aria-hidden="true"
  >
    <!-- 알림 -->
    <v-snackbar
      v-model="acceptFlag"
      top
      right
      :timeout="2000"
      color="#356859"
      class="mr-3"
    >
      승인이 완료되었습니다.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="acceptFlag = false">Close</v-btn>
      </template>
    </v-snackbar>
    <v-snackbar
      v-model="rejectFlag"
      top
      right
      :timeout="2000"
      color="crimson"
      class="mr-3"
    >
      삭제되었습니다.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="rejectFlag = false">Close</v-btn>
      </template>
    </v-snackbar>

    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header ">
          <p
            class="modal-title my-auto ml-2"
            id="exampleModalLabel"
            style="font-weight:bold; font-size:1.3rem;text-align:center;"
          >
            <span style="color:#356859">{{ wait.title }}</span> <span style="font-size:0.9rem;">의 상세정보</span>
          </p>
          <button
            type="button"
            class="close my-auto"
            data-dismiss="modal"
            aria-label="Close"
          >
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <!-- 사진 carousel -->
          <v-row justify="center">
            <v-col cols="11" class="p-0">
              <v-carousel hide-delimiters width="100%" height="200px">
                <v-carousel-item
                  v-for="(item, i) in waitImages"
                  :key="i"
                  reverse-transition="fade-transition"
                  transition="fade-transition"
                >
                  <img :src="getImg(item)" style="height:100%; width:100%;" />
                </v-carousel-item>
              </v-carousel>

              <v-row >
                <v-col cols="3" class="text-left my-auto">
                  <span style="font-size:1.1rem; font-weight:bold;"
                    >카테고리</span
                  >
                </v-col>
                <v-col cols="9" class="text-left pl-3 my-auto">
                  <span style="font-weight:bold">{{ wait.category }}</span>
                </v-col>
              </v-row>
              <v-row >
                <v-col cols="3" class="text-left my-auto pt-0">
                  <span style="font-size:1.1rem; font-weight:bold;"
                    >작성자</span
                  >
                </v-col>
                <v-col cols="9" class="text-left pl-3 my-auto pt-0">
                  <span style="font-weight:bold">{{ wait.writer }}</span>
                </v-col>
              </v-row>
              <v-row >
                <v-col cols="3" class="text-left my-auto pt-0">
                  <span style="font-size:1.1rem; font-weight:bold;"
                    >마감일</span
                  >
                </v-col>
                <v-col cols="9" class="text-left pl-3 my-auto pt-0">
                  <span style="font-weight:bold">{{ wait.edate }}</span>
                </v-col>
              </v-row>
              <v-row >
                <v-col cols="3" class="text-left my-auto pt-0">
                  <span style="font-size:1.1rem; font-weight:bold;"
                    >마감개수</span
                  >
                </v-col>
                <v-col cols="9" class="text-left pl-3 my-auto pt-0">
                  <span style="font-weight:bold">{{ wait.maxcnt }}장</span>
                </v-col>
              </v-row>
                <span style="font-size:1.1rem; font-weight:bold;"
                    >상세설명</span
                  >
              
              <p class="mt-1">{{ wait.content }}</p>
            </v-col>
          </v-row>
        </div>
        <div class="modal-footer d-flex justify-content-end py-1">
          <!--Dialog-->
          <!-- <v-dialog v-model="dialog" persistent max-width="290">
            <template v-slot:activator="{ on }"> -->
          <v-btn outlined @click="accept()" style="font-weight:bold;"
            >승인</v-btn
          >
          <v-btn outlined @click="reject()" style="font-weight:bold;"
            >거절</v-btn
          >
          <!-- </template>
            <v-card>
              <v-card-title class="headline"> 거절하시겠습니까? </v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="green darken-1" text @click="dialog = false">
                  예
                </v-btn>
                <v-btn color="green darken-1" text @click="dialog = false">
                  아니오
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog> -->
        </div>
      </div>
    </div>
  </v-container>
</template>

<script>
import http from "@/util/http-common.js";

export default {
  data() {
    return {
      acceptFlag: false,
      rejectFlag: false,
      dialog: false
    };
  },
  props: {
    wait: [Object, Array],
    waitImages: [Object, Array]
  },
  created() {},
  methods: {
    getImg(img) {
      return "../../../contents/" + img;
    },
    accept() {
      http
        .put("/donation/approve/" + this.wait.donationid)
        .then(res => {
          this.acceptFlag = true;
          setTimeout(() => {
            this.$router.go().catch(err => {
              console.log(err);
            });
          }, 2000);
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    },
    reject() {
      http
        .delete("/donation/delete/" + this.wait.donationid)
        .then(res => {
          console.log(res);
          this.rejectFlag = true;
          setTimeout(() => {
            this.$router.go().catch(err => {
              console.log(err);
            });
          }, 2000);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>

<style></style>
