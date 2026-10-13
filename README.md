# zjz-app

一个干净的 **Android + Jetpack Compose** 证件照 / 文档处理工具。

不含任何第三方广告 SDK、加固壳或第三方业务代码。功能与页面结构依据源样本 `zjz.apk`（免费证件照 v1.6.3）的
AndroidManifest 组件拓扑与资源文案重构而来 —— 详细依据见 `../zjz-extract/zjz-extract-分析报告.md`。

## 环境要求

- JDK 17（本机：`C:\Program Files\Java\jdk-17.0.11`，已在 `gradle.properties` 固定）
- Android SDK（`local.properties` 中 `sdk.dir`）
  - Platform 34、Build-Tools 34.0.0、Platform-Tools
- Gradle：项目自带 wrapper（gradle-8.8-bin）

## 构建

```bash
cd D:\ai-work\zjz-app
.\gradlew.bat assembleDebug
```

产物：`app\build\outputs\apk\debug\app-debug.apk`

## 安装运行

```bash
.\gradlew.bat installDebug
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

## 功能地图

| 分组 | 功能 | 路由 id |
|---|---|---|
| 证件照 | 证件照、身份证复印件、换底色、打印排版 | `id_photo` `id_card_copy` `change_bg` `print_layout` |
| 图片处理 | 裁剪、缩放、擦除、合成、水印、放大、黑白上色 | `crop` `resize` `eraser` `compose` `watermark` `upscale` `colorize` |
| PDF 与文档 | 生成 PDF、PDF 预览、文档扫描 | `pdf_create` `pdf_viewer` `doc_scan` |

账号（登录/注册）、会员（VIP 套餐 + 支付宝/微信）、我的文档、设置、关于为二级页面。

## 项目结构

```
zjz-app/
├─ settings.gradle.kts / build.gradle.kts / gradle.properties
├─ gradle/libs.versions.toml
└─ app/
   ├─ build.gradle.kts
   └─ src/main/
      ├─ AndroidManifest.xml
      ├─ java/com/example/zjzapp/
      │  ├─ MainActivity.kt                    # 入口，按路由决定是否显示底栏
      │  ├─ data/
      │  │  ├─ model/                          # PhotoSpec / ToolItem / DocItem / UserProfile / VipPlan
      │  │  ├─ catalog/FeatureCatalog.kt       # 单一功能目录（id ↔ 路由 ↔ 图标 key）
      │  │  └─ repo/                           # UserRepository / DocRepository（内存实现）
      │  └─ ui/
      │     ├─ navigation/                     # Routes / TabDest / BottomBar / AppNavHost
      │     ├─ component/                      # PageScaffold / FeatureCard / Common / EmptyState / AppIcon
      │     ├─ screen/
      │     │  ├─ home/ tools/ profile/        # 三个底部 Tab
      │     │  ├─ auth/ vip/ docs/ settings/ about/
      │     │  └─ feature/FeatureScreen.kt     # 统一功能页 feature/{featureId}
      │     └─ theme/
      └─ res/
```

## 扩展指引

1. **新增功能**：在 `FeatureCatalog` 加一条 `ToolItem`，在 `AppIcon.iconFor` 补图标 key 映射，
   `FeatureScreen` 里按需加分支即可 —— 路由无需改动（统一走 `feature/{id}`）。
2. **接后端**：`data/repo/` 下把内存实现换成 Retrofit/OkHttp 实现，UI 通过 `StateFlow` 订阅，不受影响。
3. **本地存储**：加 `androidx.room`，替换 `DocRepository` 的样例数据。
4. **图像能力**（按需引入，均为公开组件）：
   - 相机：`androidx.camera`（CameraX）
   - 抠图/换底：TensorFlow Lite + HED 模型（`hed_lite_model_quantize.tflite`）
   - PDF：Pdfium / `android.graphics.pdf.PdfDocument`
   - 裁剪：自研或 uCrop
5. **支付与登录**：接入自有后端 + 短信验证码；支付按需接入支付宝/微信官方 SDK（需自有商户资质）。

> 注：源样本中的穿山甲广告、360 加固、友盟统计**不予引入**。
