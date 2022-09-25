# 2022-SW-hackathon
## 컬러풀여행(Colorful Travel) <a href="https://github.com/yang1318/2022-SW-hackathon"><img src="https://img.shields.io/badge/GitHub-000000?style=flat-square&logo=GitHub&logoColor=white"/></a>
- 팀소개 : **나래** - 양선아, 이다현, 박희민
- 제출 세션 및 주제 :[특별세션] - **랜드마크 연계 관광자원 발굴 / 비체계적인 관광 서비스**

<img width="159" alt="스크린샷 2022-09-25 오전 5 38 27" src="https://user-images.githubusercontent.com/63386322/192128210-20db1391-411c-4322-bd35-79953db7bf17.png">

  
### 🌈 프로젝트 한 줄 설명
  - NFC 기능을 통한 대구 스탬프 관광 어플리케이션
### 🌈 프로젝트에 대한 설명
#### ✔️ 프로젝트 소개
  
  Colorful Travel은 스탬프 형식의 대구 관광 어플리케이션입니다. 대구의 유명 명소들을 지도를 통해 평점과 함께 한눈에 확인할 수 있고, 각 명소들에는 여러 스탬프가 등록되어 있습니다. 스탬프는 해당 명소 내에 있는 볼거리들로, 예를 들어 경북대학교가 명소라면 일청담, 북문, 센트럴 파크 등이 스탬프로 등록되어 있습니다. 스탬프에 해당하는 장소에는 NFC 태그 스티커가 붙여져있고, 그 스티커에 휴대폰을 가져다대면 Colorful Travel 앱 내에서 해당 스탬프의 장소에 방문 처리가 되고 스탬프 상세 페이지가 뜹니다. 스탬프 상세페이지에는 해당 스탬프에 달린 댓글과 챌린지를 확인할 수 있습니다. 관광지에 있을 때 벽에 작게 낙서를 해두듯이, 댓글을 작성할 수 있습니다. 낙서처럼 익명으로 댓글이 남게 됩니다. 챌린지는 해당 스탬프의 장소에서 찍은 사진을 업로드 하는 공간으로, 스탬프마다 "83타워가 나오도록 야경 찍기", "가장 자신있는 포즈로 찰칵~!" 등과 같이 재미있는 컨셉을 정해두었습니다. 또, 스탬프를 모은 개수를 통해 다른사람들과 경쟁할 수 있습니다.   

#### ✔️ 프로젝트 목적

  - 간편하고 쉽게 NFC를 통한 스탬프 확인
  - 곳곳에 부착된 NFC 태그를 찾아서 대구의 관광지 구석구석을 확인하게됨
  - 챌린지 참여, 댓글 기능을 통해 소통하며 친밀감 형성
  - 랭킹, 추첨 등의 기능을 통해 대구의 관광지를 탐방할 의욕을 돋구어줌
  
#### ✔️ 주요 기능 소개

##### 1️⃣ NFC를 통한 스탬프 확인<br>
  NFC(Near Field Communication)는 근거리 무선 통신 기술입니다.<br>
  평소 일상생활에서 사용하는 교통카드, 삼성페이 등 모두 NFC 기술로 구현되어 간편하게 가져다 대기만 하면 원하는 동작이 자동 실행됩니다.<br>
  저희는 이러한 NFC의 간편함을 이용하여, **미리 커스텀된 NFC 태그 스티커에 휴대폰을 가져다대면 Colorful travel 어플이 자동실행되어 스탬프가 자동으로 방문처리**되도록 하였습니다.<br>  
##### 2️⃣ 댓글, 챌린지 기능<br>
  NFC를 찍어 스탬프 방문처리가 되면 해당 스탬프에 댓글을 달 수 있고, 챌린지에 참여할 수 있습니다.<br>
  벽의 낙서, 자물쇠 달기 등 사람들은 유명한 관광지에 오면 사소한 낙서라도 남기는 것을 즐깁니다.<br>
  저희는 이에 착안해서 해당 스탬프 내에 익명으로 댓글을 달 수 있도록 하였고, 챌린지에는 특정 컨셉의 사진을 올려 좋아요를 주고받으며 소통할 수 있도록 하였습니다. "20220925 양선아 왔다감" 과 같이 흔적을 남기거나, "내가 1등!!" 처럼 가벼운 주제부터 해당 장소에 대한 소통까지 댓글 기능을 통해 할 수 있고, 챌린지는 "일청담 연못의 벚꽃모양이 잘나오도록 사진 찍기", "융복합관 앞에서 가장 특이한 포즈로 사진찍어보기" 등 저희가 특정한 컨셉을 정해주고, 사용자가 그에 맞는 사진을 올릴 수 있도록 하였습니다.<br>
##### 3️⃣ 숨겨진 스탬프, 추첨제도<br>
  - 대부분의 스탬프는 "융복합관", "북문" 이렇게 장소와 사진이 명시되어있지만, 몇몇 스탬프는 사진과 이름이 보이지 않도록 숨겨져있습니다. 그래서 **관광명소를 직접 구석구석, 보물찾기 하듯이** 숨겨진 스탬프를 찾을 수 있습니다. 사용자는 보물찾기를 하듯 스탬프를 찾는 재미를 느낌과 동시에 **해당 관광지를 더욱 깊게 탐구**할 수 있을 것입니다.<br>
  - 대구 전체 관광지에서 얻은 스탬프 개수에 따른 **전체 랭킹**을 확인할 수 있습니다. 사용자는 스탬프를 모으는 재미와 순위가 오르는 재미를 동시에 느낄 수 있을 것입니다.<br>
  - 한 관광지의 전체 스템프를 모으면 **소소한 선물을 추첨**하여 줍니다. 바나나우유, 스타벅스 아메리카 등 사용자에게는 스탬프를 모두 얻기위한 동기부여가 더욱 될 것입니다.<br>
 #### ✔️ 프로젝트 기대효과 및 활용방안

  - 사용자가 스탬프를 모으기 위해 관광지를 더 둘러볼수록 주변 상권도 활성화 될 것입니다.<br>
  - 입장료가 필요한 관광지의 경우 관람자가 늘어나게 되어 수익 또한 더욱 얻을 수 있을 것입니다.<br>
  - 대구에 놀러왔을 때, 관광지를 찾아볼 필요 없이 해당 어플에 등록된 관광지 중 평점을 참고하여 고르고, 관광지에 가서도 코스 또한 스탬프를 활용하여 체계적으로 짤 수 있습니다.<br>
  - 박물관 등 입장료가 필요한 경우, 이미 한번 스탬프를 다 모은 사용자에게는 다음 입장료를 할인해주는 정책을 도입하여 사용자에게 동기부여 해줄 수 있습니다.<br>

### 프로젝트에 활용된 기술 (3가지 이내)

<img src="https://img.shields.io/badge/Firebase-FFCA28?style=flat-square&logo=firebase&logoColor=white"/> - Firebase Realtime Database를 활용한 데이터베이스 개발

<img src="https://img.shields.io/badge/NFC-000080?style=flat-square&logo=NFC&logoColor=white"/> - NFC 태그를 통한 정보 읽기/쓰기

<img src="https://img.shields.io/badge/AndroidStudio-0c70f2?style=flat-square&logo=AndroidStudio&logoColor=92b8b1"/> - java를 사용한 안드로이드 어플리케이션 개발,

### 시연 영상 및 관련 링크
- 시연영상
  - https://youtu.be/h3-Zik_BrLs
  - https://youtu.be/1gy0X9xxZDE
- 피그마 링크<br>
  https://www.figma.com/file/22Ef9TSZD49QtJ5m99b9Qz/%ED%95%B4%EC%BB%A4%ED%86%A4-2022?node-id=0%3A1/  
- firebase realtime database json 파일 구조 (json파일) (NoSQL 형식)<br>
  https://drive.google.com/file/d/19saeEEb6mJWKzD8rNi88tv64nx0eVpce/view?usp=sharing
### 기타
  - 올바른 어플리케이션 실행을 위해서는 Firebase와 연동된 google-services.json 파일이 app 내에 존재해야합니다. 보안상의 문제로 github에 업로드 되지 않았습니다. 다만, 임의의 google-services.json파일을 업로드 했을 경우 해당 데이터베이스와 연동이 되어있지 않아 정보가 뜨지 않을 수 있음. 이때, 위의 구글드라이브내의 realtime database json 파일을 다운받아 설정하거나 메일로 문의해주시면 조치해드리도록 하겠습니다.
  - 저희가 release 버전이 아니라 debug버전을 사용했기때문에, 카카오지도를 보기 위해서는 KaKao Developers의 내 애플리케이션 > 앱 설정 > 플랫폼에서 해당 노트북의 키 해시값이 등록되어있어야합니다. 이러한 문제가 생긴다면 메일로 문의해주시면 조치해드리도록 하겠습니다.
  - 키 해시값 등록, google-services.json 파일 문의, 또는 기타 추가 문의는 **amy1353@naver.com** 로 연락주시면 답변드리겠습니다.
