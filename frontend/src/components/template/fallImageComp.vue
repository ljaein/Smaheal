<template>
    <div>
      <v-sheet
        class="mx-auto mt-5"
        max-width="800"
      >
        <h5>
          * 넓이가 1300px 이상인 화면에서 보는 것을 추천드립니다.
        </h5>
        <v-card width="900" height="400"
          class="mx-auto" :img="require(`@/assets/template/fall-background.jpg`)"
          dark
          elevation="8"
          >
          <v-slide-group
            class="pa-4"
            prev-icon="mdi-code-less-than"
            next-icon="mdi-code-greater-than"
            :show-arrows="true"
          >
            <v-slide-item
              v-for="n in 15"
              :key="n"
            >
              <v-card
                class="ma-4"
                width="300"
                light
              >
                <v-fab-transition>
                  <v-btn
                  color="white"
                  fab
                  small
                  absolute
                  top
                  left
                  icon
                  >
                    <v-icon>mdi-emoticon-excited</v-icon>
                  </v-btn>
                </v-fab-transition>
                <v-img
                  class="white--text align-end"
                  height="250px"
                  src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                >
                </v-img>
                <v-card-title>별명이 들어갈 자리
                  <v-spacer></v-spacer>
                  <v-card-actions>
                  <v-btn
                    icon
                    @click="show = !show"
                  >
                    <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
                  </v-btn>
                </v-card-actions>

                <v-expand-transition>
                  <div v-show="show">
                    <v-card-text>
                      I'm a thing. But, like most politicians, 
                    </v-card-text>
                  </div>
                </v-expand-transition>
                </v-card-title>
              </v-card>
            </v-slide-item>
          </v-slide-group>
        </v-card>
      </v-sheet>
    </div>
</template>

<script>
export default {
    name: "SlideImageComp",
    data: function() {
        return {
          show: false,
          showArrows: true,
        }
    }
}
</script>