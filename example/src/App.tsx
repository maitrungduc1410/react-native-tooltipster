import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import {
  Platform,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
  Image,
} from 'react-native';
import Tooltipster from 'react-native-tooltipster';
import Icon from 'react-native-vector-icons/AntDesign';

function HomeScreen() {
  return (
    <View style={styles.container}>
      <View
        style={{
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'space-between',
        }}
      >
        <Tooltipster
          text="Lorem Ipsum is simply dummy text of the printing and typesetting industry"
          bgColor="#ffbd00"
          position="top"
        >
          <TouchableOpacity
            style={[styles.button, { backgroundColor: '#ffbd00' }]}
          >
            <Icon name={'arrowup'} size={24} color={'white'} />
          </TouchableOpacity>
        </Tooltipster>
        <Tooltipster
          text="Normal size arrow"
          bgColor="#8c2f39"
          position="left"
          arrowPositionRules="ALIGN_BUBBLE"
          padding={{ right: Platform.select({ ios: 15, android: undefined }) }}
        >
          <TouchableOpacity
            style={[styles.button, { backgroundColor: '#8c2f39' }]}
          >
            <Icon name={'arrowleft'} size={24} color={'white'} />
          </TouchableOpacity>
        </Tooltipster>
      </View>
      <View
        style={{
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'space-between',
        }}
      >
        <Tooltipster
          text="With Big Arrow"
          bgColor="#17c3b2"
          position="right"
          arrowPositionRules="ALIGN_BUBBLE"
          padding={{
            right: Platform.select({ ios: 30, android: undefined }),
            top: Platform.select({ ios: 20, android: undefined }),
            bottom: Platform.select({ ios: 0, android: undefined }),
          }}
          arrowSize={20}
        >
          <TouchableOpacity
            style={[styles.button, { backgroundColor: '#17c3b2' }]}
          >
            <Icon name={'arrowright'} size={24} color={'white'} />
          </TouchableOpacity>
        </Tooltipster>
        {Platform.OS === 'ios' && (
          <Tooltipster
            bgColor="#7A316F"
            animation="FADE"
            renderTemplate={() => (
              <>
                <Text
                  style={{ color: 'white', fontSize: 20, textAlign: 'center' }}
                >
                  With React Component
                </Text>
                <Image
                  source={require('./assets/bunny.jpg')}
                  style={{
                    width: 200,
                    height: 100,
                    resizeMode: 'stretch',
                  }}
                />
              </>
            )}
          >
            <TouchableOpacity
              style={[styles.button, { backgroundColor: '#7A316F' }]}
            >
              <Icon name={'arrowdown'} size={24} color={'white'} />
            </TouchableOpacity>
          </Tooltipster>
        )}

        <Tooltipster
          text="With more rounded corners"
          bgColor="#1e88e5"
          position="left"
          arrowPositionRules="ALIGN_BUBBLE"
          padding={{ right: Platform.select({ ios: 15, android: undefined }) }}
          cornerRadius={20}
        >
          <TouchableOpacity
            style={[styles.button, { backgroundColor: '#1e88e5' }]}
          >
            <Icon name={'arrowleft'} size={24} color={'white'} />
          </TouchableOpacity>
        </Tooltipster>
      </View>
      <View
        style={{
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'space-between',
        }}
      >
        <Tooltipster
          text="With bigger text, bold and RED"
          bgColor="#241468"
          textColor="red"
          position="right"
          padding={{ right: Platform.select({ ios: 15, android: undefined }) }}
          animation="FADE"
          fontSize={22}
          fontWeight="BOLD"
        >
          <TouchableOpacity
            style={[styles.button, { backgroundColor: '#241468' }]}
          >
            <Icon name={'arrowright'} size={24} color={'white'} />
          </TouchableOpacity>
        </Tooltipster>
        <Tooltipster
          text="With bold and italic"
          bgColor="#e71d36"
          position="left"
          padding={{ right: Platform.select({ ios: 15, android: undefined }) }}
          fontSize={22}
          fontWeight="BOLD_ITALIC"
        >
          <TouchableOpacity
            style={[styles.button, { backgroundColor: '#e71d36' }]}
          >
            <Icon name={'arrowleft'} size={24} color={'white'} />
          </TouchableOpacity>
        </Tooltipster>
      </View>
    </View>
  );
}

function SettingsScreen() {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Settings!</Text>
    </View>
  );
}

const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={({ route }) => ({
          // eslint-disable-next-line react/no-unstable-nested-components
          tabBarIcon: ({ focused, color, size }) => {
            let iconName;

            if (route.name === 'Home') {
              iconName = focused ? 'star' : 'staro';
              return (
                <Tooltipster
                  text="Your favorite posts"
                  bgColor="#38a3a5"
                  textColor="#f72585"
                >
                  <Icon name={iconName!} size={size} color={color} />
                </Tooltipster>
              );
            } else if (route.name === 'Settings') {
              iconName = focused ? 'heart' : 'hearto';
              return (
                <Tooltipster
                  text="Setup something"
                  bgColor="#f77f00"
                  position="left"
                  arrowPositionRules="ALIGN_BUBBLE"
                  padding={{
                    right: Platform.select({ ios: 15, android: undefined }),
                  }}
                >
                  <Icon name={iconName!} size={size} color={color} />
                </Tooltipster>
              );
            }
          },
          tabBarActiveTintColor: 'tomato',
          tabBarInactiveTintColor: 'gray',
          // eslint-disable-next-line react/no-unstable-nested-components
          headerTitle: () => (
            <View
              style={Platform.select({
                android: {
                  width: 300,
                  alignItems: 'center',
                },
                ios: undefined,
              })}
            >
              <Tooltipster
                text="Lorem Ipsum is simply dummy text of the printing and typesetting industry"
                bgColor="#F11A7B"
              >
                <TouchableOpacity
                  style={[styles.button, { backgroundColor: '#F11A7B' }]}
                >
                  <Icon name={'arrowdown'} size={24} color={'white'} />
                </TouchableOpacity>
              </Tooltipster>
            </View>
          ),
          // eslint-disable-next-line react/no-unstable-nested-components
          headerLeft: () => (
            <Tooltipster
              text="Lorem Ipsum is simply dummy text of the printing and typesetting industry"
              bgColor="#E48586"
              position="right"
              padding={{
                right: Platform.select({ ios: 15, android: undefined }),
              }}
              animation="FADE"
              arrowPositionRules="ALIGN_BUBBLE"
              maxWidth={150}
            >
              <TouchableOpacity
                style={[styles.button, { backgroundColor: '#E48586' }]}
              >
                <Icon name={'arrowright'} size={24} color={'white'} />
              </TouchableOpacity>
            </Tooltipster>
          ),
          // eslint-disable-next-line react/no-unstable-nested-components
          headerRight: () => (
            <Tooltipster
              text="Lorem Ipsum is simply dummy text of the printing and typesetting industry"
              bgColor="#3a86ff"
              position="left"
              arrowPositionRules="ALIGN_BUBBLE"
              padding={{
                right: Platform.select({ ios: 15, android: undefined }),
              }}
              maxWidth={50}
            >
              <TouchableOpacity
                style={[styles.button, { backgroundColor: '#3a86ff' }]}
              >
                <Icon name={'arrowleft'} size={24} color={'white'} />
              </TouchableOpacity>
            </Tooltipster>
          ),
        })}
      >
        <Tab.Screen name="Home" component={HomeScreen} />
        <Tab.Screen name="Settings" component={SettingsScreen} />
      </Tab.Navigator>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'space-between',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  button: {
    borderRadius: 4,
    width: 36,
    height: 28,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
