![image](https://github.com/hyewonkim1996/Java-Swing-Project/assets/153244876/5e75b014-0f7b-4041-83a6-499be657707f)


&nbsp;
&nbsp;

<h1 align="center"> 📚 나는 개준생이다 🧐 </h1>

&nbsp;
&nbsp;

## 👩🏻‍💻 개발 인원
> 1인 개발

> 담당 구현 기능 : 로그인, 회원가입, 면접 연습, 개념 퀴즈, 자료 관리

> 기여도 : 100%

&nbsp;
&nbsp;

## 📆 프로젝트 기간
> 2023.11.20 ~ 2023.11.30

&nbsp;
&nbsp;

## 📋 프로젝트 개요 및 목표
> 개발 동기 : 개발자 취업 준비생 증가
> 개발 목적 : 개발자 취업 준비생 학습 플랫폼 제공

> 개인 목표 : 객체지향 프로그래밍, 인터페이스 및 컬렉션 프레임워크 적용하여 이해도 높이기 / Java Swing 활용하여 자바만으로 시각적인 프로그램 만들기 

> 🔗 [프로젝트 시연 영상](https://www.youtube.com/watch?v=GbE_Ux2hzKc)

> 🔗 [프로젝트 PDF](https://github.com/hyewonkim1996/Java-Swing-Project/blob/main/JAVA%20SWING%20%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20-%20%EB%82%98%EB%8A%94%20%EA%B0%9C%EC%A4%80%EC%83%9D%EC%9D%B4%EB%8B%A4.pdf)

&nbsp;
&nbsp;

## 🚧 개발 환경 
![image](https://github.com/hyewonkim1996/Java-Swing-Project/assets/153244876/ef3400da-cf6e-4638-8ff9-00497f06684d)

&nbsp;

## ❓ 주요 사용 기술과 이유

|사용 기술|프로젝트 적용 범위|🌟이유🌟|
|------|---|---|
|객체지향 프로그래밍 - 추상화|추상 클래스|DAO별 공통된 메소드를 추상화하여 유지보수 용이|
|객체지향 프로그래밍 - 상속|DAO 클래스, JFrame|중복 코드 방지|
|객체지향 프로그래밍 - 캡슐화|DTO 클래스|데이터 보안 강화|
|객체지향 프로그래밍 - 다형성|메소드 오버로딩, 오버라이딩|중복 코드 방지|
|Java Swing|프로그램 화면 디자인|실제 프로그램처럼 디자인이 가능해 단순히 콘솔에 출력되는 것보다 사용하기 편하며 학습 의욕 향상|
|컬렉션 프레임워크 - ArrayList|개념 퀴즈 조회, 퀴즈 보기에 체크박스와 스크롤 패널 적용|배열과 달리 선언 시 길이를 지정할 필요가 없어 편리함|
|컬렉션 프레임워크 - HashMap|회원별 면접답변 조회|회원 아이디를 key, 회원이 답변한 질문번호를 value로 설정해 두 가지 데이터를 하나의 참조변수에 저장 가능|
|인터페이스|GUI 디자인 배경, 버튼 이미지 설정|이미지 경로를 인터페이스로 통일하여 이미지 경로 수정시 유지보수 용이|
|StringBuilder|DB<->프로그램 간 퀴즈 보기, 정답 문자 변환|String으로 객체 생성 남발하지 않고 메모리 절약|
|디자인 패턴 - 싱글톤|DAO 및 기타 클래스|객체 생성 남발 방지해 메모리 절약|

&nbsp;

## 💡 배운 점

> **1) 객체지향 프로그래밍과 인터페이스의 중요성**

```
😄 : 추상화, 상속, 다형성, 인터페이스로 정말 많은 중복 코드가 방지된다는 사실을 깨달았다.
      예를 들어 AWT 화면과 버튼에 쓰이는 이미지의 경로를 설정하는 부분을 인터페이스로 추출하였다.
      만약 인터페이스로 추출하지 않았다면 이미지 경로 변경 시 코드를 20여 번 수정해야 하지만,
      인터페이스 추출을 통해 단 한번의 코드 수정으로 이미지 경로를 변경할 수 있게 되면서
      '유지보수 용이'가 어떤 의미인지 체득할 수 있었다.
```
&nbsp;

> **2) 객체와 클래스의 의미**

```
😄 : JAVA AWT로 사용자에게 보이는 화면과 기능을 구현하는 코드를 각각 만들며 
      구현할 대상인 객체와 객체를 구현하는 설계도인 클래스의 의미를 체득할 수 있었다.
```
&nbsp;

> **3) 사용자 입장에서 편리한 기능에 대한 고찰**

```
😄 : 자료 관리 - 개념 퀴즈 등록 기능
      사용자 입장에서는 사용하는 페이지가 적을수록 편리하므로 
      등록해야 할 컬럼이 3가지인 상황에서 페이지를 최소한으로 사용하는 방법에 대해 깊게 고찰한 결과
      한 페이지에서 버튼을 달리해 3가지 컬럼을 등록하는 방법을 고안해 냈다.

😥 : 개념 퀴즈 기능
      현재는 List에 담은 문제를 순차적으로 객체를 새로 생성해 반영하는 방식으로 구현했지만
      사용자 입장에서는 문제를 자유롭게 이동할 수 없어 편리하지 않아 개선이 필요하다. 
```
&nbsp;

