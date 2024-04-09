import vue from "@vitejs/plugin-vue";
import { defineConfig } from "vite";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import Icons from "unplugin-icons/vite";
import IconsResolver from "unplugin-icons/resolver";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";
import UnoCSS from "unocss/vite";
import { resolve } from "path";

export default defineConfig(({ mode }) => {
  return {
    plugins: [
      vue(),
      UnoCSS({
        configFile: "uno.config.ts"
      }),
      AutoImport({
        include: [
          /\.[tj]sx?$/, // .ts, .tsx, .js, .jsx
          /\.vue$/,
          /\.vue\?vue/ // .vue
        ],
        imports: [
          "vue",
          "vue-router",
          {
            "@common/constants": ["Consts"],
            "@vueuse/core": ["useStorage", "useStorageAsync"]
          },
          {
            from: "element-plus",
            imports: ["FormInstance", "FormRules", "TabsPaneContext"],
            type: true
          }
        ],
        resolvers: [
          ElementPlusResolver(),
          IconsResolver({
            prefix: "Icon"
          })
        ],
        vueTemplate: true,
        dts: "./auto-imports.d.ts"
      }),
      Components({
        resolvers: [
          ElementPlusResolver(),
          IconsResolver({
            enabledCollections: ["ep"]
          })
        ],
        dirs: ["common/components"]
      }),
      Icons({
        autoInstall: true
      })
    ],
    build: {
      target: "modules",
      outDir: "dist/",
      assetsDir: "static",
      sourcemap: true,
      rollupOptions: {
        input: {
          mainapp: resolve(__dirname, "index.html")
        },
        output: {
          entryFileNames: "static/js/[name]-[hash].js",
          chunkFileNames: "static/js/[name]-[hash].js",
          assetFileNames: "static/[ext]/name-[hash].[ext]"
        }
      }
    },
    resolve: {
      alias: {
        "@mainapp": resolve(__dirname, "apps/mainapp"),
        "@subapp": resolve(__dirname, "apps/subapp"),
        "@common": resolve(__dirname, "common")
      }
    },
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: `@use "sass:math";`
        }
      }
    }
  };
});
