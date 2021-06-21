<template>
  <v-row class="profilebody">
    <v-col class="content">
      <v-row class="cardrow">
        <v-col class="card">
          <v-card class="card" elevation="2" tile>
            <div class="cardcontent">
              <h1>Welcome {{ $auth.user.nickname }} !</h1>
              <h2 class="pt-10">Quote of the session</h2>
              <p class="pt-5">"{{ this.randomQuote.text }}"</p>
              <p>- {{ this.randomQuote.author }}</p>
            </div></v-card
          >
        </v-col>
      </v-row>
      <div class="clearancecontainer pb-10">
        <h1 style="text-align: center">Your clearance cards</h1>
        <v-carousel class="clearancelist">
          <v-carousel-item v-for="role in roles" :key="role">
            <v-img
              alt=" Logo"
              class="shrink mr-2 logo"
              contain
              src="../assets/productio.png"
              transition="scale-transition"
              width="40"
            />
            <h1 style="color: black; text-align: center; padding-top: 27%">
              {{ role.split('_', 1)[0] }}
            </h1>
          </v-carousel-item>
        </v-carousel>
      </div>
    </v-col>
    <v-col class="content">
      <v-row class="infocardrow">
        <v-col class="card">
          <v-card class="card" elevation="2" tile>
            <div class="infocardcontent">
              <h1>Your information</h1>
              <v-row class="inforow">
                <v-dialog v-model="dialogemail" width="500">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn v-bind="attrs" v-on="on" depressed>
                      <v-col cols="1"><p>Email</p></v-col>
                      <v-col style="text-align: center"><p>|</p></v-col>
                      <v-col cols="4"
                        ><p>{{ $auth.user.email }}</p></v-col
                      >
                      <v-col style="text-align: center"><p>|</p></v-col>
                      <v-col><i class="fas fa-chevron-right"></i></v-col
                    ></v-btn>
                  </template>

                  <v-card>
                    <v-card-title class="headline grey lighten-2">
                      Change email
                    </v-card-title>

                    <v-text-field
                      class="pa-5"
                      label="Main input"
                      hide-details="auto"
                      v-model="emailinput"
                    ></v-text-field>

                    <v-divider></v-divider>

                    <v-card-actions>
                      <v-row>
                        <v-btn color="primary" text @click="dialogemail = false">
                          Cancel
                        </v-btn>
                        <v-btn color="primary" text @click="updateUser()">
                          Change email
                        </v-btn>
                      </v-row>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-row>

              <v-row class="inforow">
                <v-dialog v-model="dialogname" width="500">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn v-bind="attrs" v-on="on" depressed>
                      <v-col cols="1"><p>Name</p></v-col>
                      <v-col style="text-align: center"><p>|</p></v-col>
                      <v-col cols="4"
                        ><p>{{ $auth.user.nickname }}</p></v-col
                      >
                      <v-col style="text-align: center"><p>|</p></v-col>
                      <v-col><i class="fas fa-chevron-right"></i></v-col
                    ></v-btn>
                  </template>

                  <v-card>
                    <v-card-title class="headline grey lighten-2">
                      Change Name
                    </v-card-title>

                    <v-text-field
                      class="pa-5"
                      label="Main input"
                      hide-details="auto"
                      v-model="nameinput"
                    ></v-text-field>

                    <v-divider></v-divider>

                    <v-card-actions>
                      <v-row>
                        <v-btn color="primary" text @click="dialogname = false">
                          Cancel
                        </v-btn>
                        <v-btn color="primary" text @click="updateUser()">
                          Change name
                        </v-btn>
                      </v-row>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-row>

              <v-row class="inforow">
                <v-dialog v-model="dialog" width="500">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn v-bind="attrs" v-on="on" depressed>
                      <v-col cols="1"><p>Password</p></v-col>
                      <v-col style="text-align: center"><p></p></v-col>
                      <v-col cols="4"><p></p></v-col>
                      <v-col style="text-align: center"><p></p></v-col>
                      <v-col><i class="fas fa-chevron-right"></i></v-col
                    ></v-btn>
                  </template>

                  <v-card>
                    <v-card-title class="headline grey lighten-2">
                      Resetting password
                    </v-card-title>

                    <v-card-text>
                      Pressing yes will send a reset password email to your
                      mailbox. Are you sure you want to reset your password?
                    </v-card-text>

                    <v-divider></v-divider>

                    <v-card-actions>
                      <v-row>
                        <v-btn color="primary" text @click="dialog = false">
                          No
                        </v-btn>
                        <v-btn color="primary" text @click="updatePassword()">
                          Yes
                        </v-btn>
                      </v-row>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-row>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<style scoped>
.clearancecontainer {
  width: 100%;
  height: 20%;
}

.v-application p {
  margin-bottom: 0px !important;
}

.fixedpad {
  padding-bottom: 0px;
}

.profilebody {
  height: 100%;
}

.inforow {
  padding-top: 3%;
}

.logo {
  padding-top: 5%;
  margin-left: 46%;
  display: block;
}

.v-btn {
  color: black;
  background-color: white !important;
  width: 100%;
}

.clearancelist {
  height: 200px;
  width: 80%;
  display: block;
  margin-left: auto;
  margin-right: auto;
  background-color: white;
}

.content {
  height: 100%;
  width: 50%;
  font-family: "PT Serif", serif;
}

.cardcontent {
  text-align: center;
  padding: 3%;
}

.infocardcontent {
  padding: 3%;
}

.cardrow {
  width: 100%;
  padding: 4%;
}

.infocardrow {
  width: 100%;
  padding: 4%;
}
</style>

<script>
import axios from "axios";
export default {
  name: "Profile",

  data() {
    return {
      nameinput: this.$auth.user.nickname,
      emailinput: this.$auth.user.email,
      randomQuote: {},
      dialog: false,
      dialogname: false,
      dialogemail: false,
      quotes: [
        {
          text: "A clever person solves a problem, A wise person AVOIDS it.",
          author: "Albert Einstein",
        },
        {
          text: "Before software can be reusable it first has to be usable.",
          author: "Ralph Johnson",
        },
        {
          text: "9 women CANNOT make a baby in ONE MONTH.",
          author: "Fred Brooks",
        },
        {
          text: "Don’t let yesterday take up too much of today.",
          author: "Will Rogers",
        },
        {
          text:
            "Failure is simply the opportunity to begin again, this time more intelligently.",
          author: "Henry Ford",
        },
        {
          text: "Today’s accomplishments were yesterday’s impossibilities.",
          author: "Robert H. Schuller",
        },
      ],

      roles: [],
    };
  },

  mounted() {
    this.randomQuote = this.quotes[
      Math.floor(Math.random() * this.quotes.length)
    ];

    this.roles = this.$auth.user["http://Productio.net/roles"];
  },

  methods: {
    async updatePassword() {
      this.dialog = false;
      const token = await this.$auth.getTokenSilently();

      const calldata = {
        id: this.$auth.user.id,
        email: this.$auth.user.email,
      };
      console.log(token);
      axios.post("http://localhost:3000/updatepassword", calldata, {
        headers: {
          Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
        },
      });
    },

    async updateUser() {
      this.dialogname = false;
      this.dialogemail = false;
      const token = await this.$auth.getTokenSilently();

      const calldata = {
        id: this.$auth.user.id,
        email: this.emailinput,
        nickname: this.nameinput,
        provider: this.$auth.user.provider,
      };
      console.log(calldata);
      axios.patch("http://localhost:3000/updateuser", calldata, {
        headers: {
          Authorization: `Bearer ${token}`, // send the access token through the 'Authorization' header
        },
      });
    },
  },
};
</script>
