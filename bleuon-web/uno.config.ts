import { defineConfig, presetAttributify, presetIcons, presetUno, transformerDirectives } from "unocss";

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
        primary: "var(--el-color-primary)"
      },
      text: {
        primary: "var(--bleuon-text-primary)",
        secondary: "var(--bleuon-text-secondary)",
        thirdly: "var(--bleuon-text-thirdly)"
      },
      bg: {
        primary: "#f6f7f8"
      },
      border: {
        primary: "var(--blueon-border-primary)"
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
