<template>
  <div>
    <v-container fluid class="mt-5">
      <v-card class="topbar">
        <v-toolbar color="#fffbe6" flat>
          <v-toolbar-title class="pl-5 tab-text basil--text">후기 게시판</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn v-if="getProfile" class="green-mbtn" @click="goReviewWrite" outlined>후기 등록</v-btn>

          <template v-slot:extension>
            <v-tabs center-active color="basil" class="basil--text" grow>
              <v-tab class="tab-text" @click="goTab(1)">아동/청소년</v-tab>
              <v-tab class="tab-text" @click="goTab(2)">어르신</v-tab>
              <v-tab class="tab-text" @click="goTab(3)">장애인</v-tab>
              <v-tab class="tab-text" @click="goTab(4)">가족/여성</v-tab>
              <v-tab class="tab-text" @click="goTab(5)">다문화</v-tab>
              <v-tab class="tab-text" @click="goTab(6)">기타</v-tab>
            </v-tabs>
          </template>
        </v-toolbar>
      </v-card>
      <!-- 아동,청소년 -->
      <v-sheet class="col-12 mb-15" elevation="8" style="height:340px;">
        <v-slide-group v-model="model" center-active show-arrows>
          <v-slide-item v-for="(item, index) in child" :key="index">
            <v-card class="ma-4 " height="280" width="200">
              <ReviewListComp
                :num="item.num"
                :nickName="item.nickName"
                :title="item.title"
                :content="item.content"
                :img="item.img"
                :likeCnt="item.likeCnt"
                :visit="item.visit"
                :createdAt="item.createdAt"
              />
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
      <!-- 어르신 -->
      <v-sheet class="col-12 mb-15" elevation="8" style="height:340px;">
        <v-slide-group v-model="model" center-active show-arrows>
          <v-slide-item v-for="(item, index) in senior" :key="index">
            <v-card class="ma-4 " height="280" width="200">
              <ReviewListComp
                :num="item.num"
                :nickName="item.nickName"
                :title="item.title"
                :content="item.content"
                :img="item.img"
                :likeCnt="item.likeCnt"
                :visit="item.visit"
                :createdAt="item.createdAt"
              />
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
      <!-- 장애인 -->
      <v-sheet class="col-12 mb-15" elevation="8" style="height:340px;">
        <v-slide-group v-model="model" center-active show-arrows>
          <v-slide-item v-for="(item, index) in disabled" :key="index">
            <v-card class="ma-4 " height="280" width="200">
              <ReviewListComp
                :num="item.num"
                :nickName="item.nickName"
                :title="item.title"
                :content="item.content"
                :img="item.img"
                :likeCnt="item.likeCnt"
                :visit="item.visit"
                :createdAt="item.createdAt"
              />
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
      <!-- 가족,여성 -->
      <v-sheet class="col-12 mb-15" elevation="8" style="height:340px;">
        <v-slide-group v-model="model" center-active show-arrows>
          <v-slide-item v-for="(item, index) in family" :key="index">
            <v-card class="ma-4 " height="280" width="200">
              <ReviewListComp
                :num="item.num"
                :nickName="item.nickName"
                :title="item.title"
                :content="item.content"
                :img="item.img"
                :likeCnt="item.likeCnt"
                :visit="item.visit"
                :createdAt="item.createdAt"
              />
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
      <!-- 다문화 -->
      <v-sheet class="col-12 mb-15" elevation="8" style="height:340px;">
        <v-slide-group v-model="model" center-active show-arrows>
          <v-slide-item v-for="(item, index) in cultural" :key="index">
            <v-card class="ma-4 " height="280" width="200">
              <ReviewListComp
                :num="item.num"
                :nickName="item.nickName"
                :title="item.title"
                :content="item.content"
                :img="item.img"
                :likeCnt="item.likeCnt"
                :visit="item.visit"
                :createdAt="item.createdAt"
              />
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
      <!-- 기타 -->
      <v-sheet class="col-12 mb-15" elevation="8" style="height:340px;">
        <v-slide-group v-model="model" center-active show-arrows>
          <v-slide-item v-for="(item, index) in etc" :key="index">
            <v-card class="ma-4 " height="280" width="200">
              <ReviewListComp
                :num="item.num"
                :nickName="item.nickName"
                :title="item.title"
                :content="item.content"
                :img="item.img"
                :likeCnt="item.likeCnt"
                :visit="item.visit"
                :createdAt="item.createdAt"
              />
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
      <div style="height:340px;"></div>
    </v-container>
    <br />
  </div>
</template>

<script>
import http from '@/util/http-common.js';
import ReviewListComp from '@/components/review/ReviewListComp.vue';
import { mapGetters } from 'vuex';

export default {
  name: 'ReviewList',
  data() {
    return {
      model: null,
      len: 0,
      child: [],
      senior: [],
      disabled: [],
      family: [],
      cultural: [],
      etc: [],
    };
  },
  components: {
    ReviewListComp,
  },
  created() {
    this.getChild();
    this.getSenior();
    this.getDiabled();
    this.getFamily();
    this.getCultural();
    this.getEtc();
  },
  computed: {
    ...mapGetters(['getProfile']),
  },
  methods: {
    goReviewWrite() {
      this.$router.push(`/reviewWrite`);
    },
    getChild() {
      http
        .get('/review/categoryList/아동,청소년')
        .then((res) => {
          this.child = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getSenior() {
      http
        .get('/review/categoryList/어르신')
        .then((res) => {
          this.senior = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getDiabled() {
      http
        .get('/review/categoryList/장애인')
        .then((res) => {
          this.disabled = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getFamily() {
      http
        .get('/review/categoryList/가족,여성')
        .then((res) => {
          this.family = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getCultural() {
      http
        .get('/review/categoryList/다문화')
        .then((res) => {
          this.cultural = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getEtc() {
      http
        .get('/review/categoryList/기타')
        .then((res) => {
          this.etc = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    goTab(num) {
      if (num == 1) {
        scroll(0, 0);
      } else if (num == 2) {
        scroll(0, 430);
      } else if (num == 3) {
        scroll(0, 830);
      } else if (num == 4) {
        scroll(0, 1230);
      } else if (num == 5) {
        scroll(0, 1630);
      } else if (num == 6) {
        scroll(0, 2030);
      }
    },
  },
  watch: {
    page(page) {
      page = (page - 1) * 8;
      http
        .get(`/review/getList/${page}`)
        .then(({ data }) => {
          this.items = data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style scoped>
.topbar {
  z-index: 10;
  position: sticky;
  top: 100px;
}
.tab-text {
  font-weight: bold;
}
</style>
