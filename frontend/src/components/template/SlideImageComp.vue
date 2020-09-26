<template>
    <div>
      <v-sheet
        class="mx-auto mt-5"
        elevation="8"
        max-width="800"
      >
        <v-slide-group
          v-model="model"
          class="pa-4"
          :prev-icon="prevIcon ? 'mdi-minus' : undefined"
          :next-icon="nextIcon ? 'mdi-plus' : undefined"
          show-arrows="true"
        >
          <v-slide-item
            v-for="n in 15"
            :key="n"
            v-slot:default="{ active, toggle }"
          >
            <v-card
              class="ma-4"
              height="300"
              width="300"
              @click="toggle"
            >
                <v-img
                    class="white--text align-end"
                    height="250px"
                    src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                >
                </v-img>
                <v-card-title>Top 10 Australian beaches</v-card-title>
              <v-row
                class="fill-height"
                align="center"
                justify="center"
              >
                <v-scale-transition>
                  <v-icon
                    v-if="active"
                    color="white"
                    size="48"
                    v-text="'mdi-close-circle-outline'"
                  ></v-icon>
                </v-scale-transition>
              </v-row>
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </v-sheet>
    </div>
</template>

<script>
export default {
    name: "SlideImageComp",
    data: function() {
        return {

        }
    }
}
</script>