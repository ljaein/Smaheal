<template>
  <div>
    <td class="text-center">{{ num }}</td>
    <td class="text-center">{{ title }}</td>
    <td class="text-center">{{ content }}</td>
    <td class="text-center">{{ nickName }}</td>
    <td class="text-center">{{ img }}</td>
    <td class="text-center">{{ likeCnt }}</td>
    <td class="text-center">{{ visit }}</td>
    <td class="text-center">{{ getFormatDate(createdAt) }}</td>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "ReviewListComp",
  props: {
    num: { type: Number },
    nickName: { type: String },
    title: { type: String },
    content: { type: String },
    img: { type: String },
    likeCnt: { type: Number },
    visit: { type: Number },
    createdAt: { type: String }
  },
  methods: {
    getFormatDate(regtime) {
      return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
    }
  }
};
</script>

<style>
</style>