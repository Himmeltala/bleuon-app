import {
  defineConfig,
  presetAttributify,
  presetIcons,
  presetUno,
  transformerDirectives
} from "unocss";

export default defineConfig({
  presets: [
    presetUno(),
    presetAttributify(),
    presetIcons({
      collections: {
        tabler: () => import("@iconify-json/tabler/icons.json").then(i => i.default),
        ep: () => import("@iconify-json/ep/icons.json").then(i => i.default)
      }
    })
  ],
  transformers: [
    transformerDirectives({
      applyVariable: ["--uno"]
    })
  ],
  theme: {
    colors: {
      theme: {
        primary: "var(--bleuon-theme-color-primary)",
        1: "var(--bleuon-theme-color-1)",
        2: "var(--bleuon-theme-color-2)",
        3: "var(--bleuon-theme-color-3)",
        4: "var(--bleuon-theme-color-4)",
        5: "var(--bleuon-theme-color-5)",
        6: "var(--bleuon-theme-color-6)"
      },
      text: {
        primary: "var(--bleuon-text-color-primary)",
        regular: "var(--bleuon-text-color-regular)",
        secondary: "var(--bleuon-text-color-secondary)",
        placeholder: "var(--bleuon-text-color-placeholder)",
        disabled: "var(--bleuon-text-color-disabled)"
      },
      bg: {
        primary: "var(--bleuon-bg-color)",
        page: "var(--bleuon-bg-color-page)",
        overlay: "var(--bleuon-bg-color-overlay)"
      },
      border: {
        primary: "var(--bleuon-border-color)",
        darker: "var(--bleuon-border-color-darker)",
        dark: "var(--bleuon-border-color-dark)",
        light: "var(--bleuon-border-color-light)",
        lighter: "var(--bleuon-border-color-lighter)",
        extraLight: "var(--bleuon-border-color-extra-light)"
      }
    }
  },
  rules: [
    [
      /^flow-(auto|hidden|inherit|initial|overlay|revert|scroll|unset|visible)$/,
      ([, d]) => ({ overflow: `${d}` })
    ]
  ],
  shortcuts: [
    // flex
    [
      /^f-((c|s|e)(-(c|s|e|b|a))*)$/,
      ([, , g1, , g2]) => {
        let style = ``;
        const temps = [
          { k: "c", v: "center" },
          { k: "s", v: "start" },
          { k: "e", v: "end" },
          { k: "b", v: "between" },
          { k: "a", v: "around" }
        ];

        const r1 = temps.find(i => i.k == g1);
        style = `flex items-${r1?.v || "center"} content-${r1?.v || "center"}`;

        if (g2) {
          const r2 = temps.find(i => i.k == g2);
          style += ` justify-${r2?.v || "center"}`;
        }

        return style;
      }
    ],
    // 悬停改变字体颜色
    [
      /^hover$/,
      () => {
        return `cursor-pointer hover:text-theme-primary transition-all-300`;
      }
    ]
  ]
});
