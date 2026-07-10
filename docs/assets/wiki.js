const translations = {
  vi: {
    meta_title: "OmniGemLevel - Wiki nâng cấp gem cho MMOItems | SalyVn",
    meta_description: "Hướng dẫn OmniGemLevel: hợp nhất gem cùng id, cùng level trong MMOItems, cấu hình max level và giữ nguyên luồng socket gốc.",
    skip: "Bỏ qua điều hướng",
    nav_quick: "Bắt đầu",
    nav_mechanics: "Cơ chế",
    nav_config: "Cấu hình",
    nav_mmoitems: "MMOItems",
    nav_faq: "FAQ",
    search_placeholder: "Tìm: GEM_ID_LEVEL, max-level, damage_1",
    hero_eyebrow: "WIKI SỬ DỤNG / MMOITEMS ADDON",
    hero_title: "Nâng cấp gem trùng loại, trùng cấp mà không tốn thêm socket.",
    hero_copy: "OmniGemLevel thêm stat GEM_ID_LEVEL và biến gem trùng id + trùng level thành gem cấp kế tiếp ngay trong slot cũ.",
    cta_start: "Bắt đầu thiết lập",
    cta_mechanics: "Xem cơ chế nâng cấp",
    fact_trigger: "KÍCH HOẠT",
    fact_trigger_value: "Drag & drop",
    fact_match: "QUY TẮC",
    fact_match_value: "Cùng id + cùng level",
    fact_default: "CẤP TỐI ĐA MẶC ĐỊNH",
    fact_runtime: "NỀN TẢNG",
    overview_title: "Same gem. Same level. One upgrade.",
    overview_copy: "Người chơi không phải hy sinh thêm một socket chỉ để tiến hóa gem đang dùng. Hai gem giống hệt nhau được hợp nhất thành template level tiếp theo.",
    quick_title: "Bắt đầu trong bốn bước.",
    quick_copy: "Dành cho server Paper 1.21 đã cài MMOItems và MythicLib. Đây là hướng dẫn sử dụng, không phải hướng dẫn build source.",
    step_1_title: "Đặt jar vào server",
    step_1_copy: "Đặt OmniGemLevel.jar vào thư mục plugins rồi khởi động lại server.",
    step_2_title: "Ánh xạ gem",
    step_2_copy: "Khai báo family, max-level và MMOItems template cho từng cấp.",
    step_3_title: "Gắn GEM_ID_LEVEL",
    step_3_copy: "Thêm stat vào từng gem stone với giá trị như damage_1.",
    step_4_title: "Drag để nâng cấp",
    step_4_copy: "Gem trùng id và level sẽ nâng slot cũ lên cấp kế tiếp.",
    mechanics_title: "Hợp nhất, không lấp thêm slot.",
    mechanics_copy: "Plugin đọc GEM_ID_LEVEL trên gem đang kéo và gem đã gắn. Khi cả family lẫn level khớp, slot cũ được thay bằng template level + 1.",
    logic_1_title: "Tìm gem trùng",
    logic_1_copy: "Đọc id và level từ stat.",
    logic_2_title: "Giải template kế tiếp",
    logic_2_copy: "Lấy MMOItems id của level + 1.",
    logic_3_title: "Nâng slot cũ",
    logic_3_copy: "Giữ các socket còn lại trống.",
    behavior_title: "Không ép đè sai gem.",
    behavior_copy: "Chỉ exact pair mới được nâng cấp. Khác id, khác level hoặc trường hợp socket không hợp lệ vẫn để MMOItems xử lý theo luồng gốc.",
    table_cursor: "Gem trên chuột",
    table_target: "Gem trên item",
    table_result: "Kết quả",
    table_empty: "Chưa có",
    table_native: "MMOItems gắn vào socket hợp lệ.",
    table_upgrade_2: "Nâng slot cũ thành damage_2.",
    table_upgrade_3: "Nâng slot cũ thành damage_3.",
    table_next_slot: "Khác level, không đè; trả về luồng MMOItems.",
    table_diff_id: "Khác id, không đè; trả về luồng MMOItems.",
    table_max: "Đã max level, không tiêu hao gem.",
    config_title: "Một config, nhiều dòng gem.",
    config_copy: "Cấp tối đa mặc định là 5. Mỗi gem family có thể ghi đè giới hạn và ánh xạ từng level sang MMOItems item id riêng.",
    config_default_title: "Global default",
    config_default_copy: "default-max-level áp dụng cho mọi family chưa có giới hạn riêng.",
    config_override_title: "Per-gem override",
    config_override_copy: "max-level trong family luôn được ưu tiên hơn giá trị global.",
    config_resolver_title: "Mapped + fallback",
    config_resolver_copy: "Map template rõ ràng hoặc bật convention fallback theo GEMID_LEVEL.",
    copy: "Sao chép",
    copied: "Đã sao chép",
    mi_title: "Gắn vào workflow MMOItems hiện có.",
    mi_copy: "Mỗi gem template chỉ cần GEM_ID_LEVEL đúng cấp. Damage, defense, socket color và các stat thật vẫn thuộc về MMOItems.",
    mi_editor_title: "Nhập stat theo family_level",
    mi_editor_copy: "Lặp lại cho damage_2, damage_3 và các cấp còn lại.",
    mi_notice_title: "GEM_ID_LEVEL chỉ đánh dấu gem.",
    mi_notice_copy: "Stat này không cộng sức mạnh trực tiếp cho item đích. Gem level mới vẫn được áp dụng bằng cơ chế MMOItems gốc.",
    faq_title: "Câu hỏi thường gặp.",
    faq_copy: "Các tình huống nên kiểm tra trước khi đưa gem progression lên server chính.",
    faq_1_q: "Thiếu template level tiếp theo thì sao?",
    faq_1_a: "Plugin không tiêu hao gem trên chuột và không sửa item. Kiểm tra config levels và MMOItems item id.",
    faq_2_q: "Nâng cấp có tính success-rate không?",
    faq_2_a: "Không. Exact-pair upgrade thành công khi template level tiếp theo tồn tại và hợp lệ.",
    faq_3_q: "Cùng id nhưng khác level có bị đè không?",
    faq_3_a: "Không. Trường hợp đó được trả về MMOItems để dùng socket hợp lệ tiếp theo nếu có.",
    faq_4_q: "Socket color và giới hạn item còn hoạt động không?",
    faq_4_a: "Có. Non-upgrade case dùng luồng MMOItems gốc; upgrade case áp dụng gem mới bằng API MMOItems.",
    search_empty: "Không tìm thấy phần phù hợp.",
    footer_copy: "Wiki sử dụng cho MMOItems gemstone progression.",
    back_top: "Lên đầu trang",
    alt_overview: "Tổng quan OmniGemLevel với hai gem cấp một hợp thành gem cấp hai",
    alt_mechanics: "Sơ đồ trước và sau khi slot gem được nâng từ cấp một lên cấp hai",
    alt_behavior: "Ma trận an toàn cho gem trùng cấp, khác cấp, khác id và max level",
    alt_config: "Cấu hình OmniGemLevel với max level global và ghi đè theo từng gem",
    alt_mmoitems: "Ba bước thêm GEM_ID_LEVEL, ánh xạ template và drag drop để nâng gem"
  },
  en: {
    meta_title: "OmniGemLevel - MMOItems Gem Upgrade Addon | SalyVn",
    meta_description: "OmniGemLevel adds same-id, same-level gemstone fusion to MMOItems. Upgrade existing sockets, set global or per-gem caps, and preserve native behavior.",
    skip: "Skip navigation",
    nav_quick: "Quick start",
    nav_mechanics: "Mechanics",
    nav_config: "Config",
    nav_mmoitems: "MMOItems",
    nav_faq: "FAQ",
    search_placeholder: "Search: GEM_ID_LEVEL, max-level, damage_1",
    hero_eyebrow: "USAGE WIKI / MMOITEMS ADDON",
    hero_title: "Upgrade exact duplicate gems without consuming another socket.",
    hero_copy: "OmniGemLevel adds GEM_ID_LEVEL and turns a same-id, same-level pair into the next configured gem inside the existing socket.",
    cta_start: "Start setup",
    cta_mechanics: "See upgrade mechanics",
    fact_trigger: "TRIGGER",
    fact_trigger_value: "Drag & drop",
    fact_match: "MATCH RULE",
    fact_match_value: "Same id + same level",
    fact_default: "DEFAULT MAX LEVEL",
    fact_runtime: "PLATFORM",
    overview_title: "Same gem. Same level. One upgrade.",
    overview_copy: "Players do not sacrifice another socket just to progress a gem they already use. Two exact duplicates resolve into the next-level template.",
    quick_title: "Get running in four steps.",
    quick_copy: "For Paper 1.21 servers with MMOItems and MythicLib installed. This is a usage guide, not a source build guide.",
    step_1_title: "Place the jar",
    step_1_copy: "Put OmniGemLevel.jar in the plugins folder and restart the server.",
    step_2_title: "Map gem families",
    step_2_copy: "Set the family, max-level, and MMOItems template for every level.",
    step_3_title: "Add GEM_ID_LEVEL",
    step_3_copy: "Add the stat to each gem stone with a value such as damage_1.",
    step_4_title: "Drag to upgrade",
    step_4_copy: "A same-id, same-level pair upgrades the existing socket.",
    mechanics_title: "Fuse, don't fill.",
    mechanics_copy: "The plugin reads GEM_ID_LEVEL from the cursor gem and applied gem. When family and level match, the old socket receives the level + 1 template.",
    logic_1_title: "Find a duplicate",
    logic_1_copy: "Read id and level from the stat.",
    logic_2_title: "Resolve next template",
    logic_2_copy: "Find the MMOItems id for level + 1.",
    logic_3_title: "Upgrade old socket",
    logic_3_copy: "Keep all remaining sockets free.",
    behavior_title: "No accidental overwrites.",
    behavior_copy: "Only an exact pair upgrades. Different ids, different levels, and invalid socket cases remain in MMOItems native handling.",
    table_cursor: "Cursor gem",
    table_target: "Gem on item",
    table_result: "Result",
    table_empty: "None",
    table_native: "MMOItems applies it to a valid socket.",
    table_upgrade_2: "Upgrade the old socket to damage_2.",
    table_upgrade_3: "Upgrade the old socket to damage_3.",
    table_next_slot: "Different level, no overwrite; return to MMOItems flow.",
    table_diff_id: "Different id, no overwrite; return to MMOItems flow.",
    table_max: "Already max level; cursor gem is not consumed.",
    config_title: "One config, unlimited gem lines.",
    config_copy: "The global max level defaults to 5. Every gem family can override its cap and map each level to a custom MMOItems item id.",
    config_default_title: "Global default",
    config_default_copy: "default-max-level applies to every family without its own cap.",
    config_override_title: "Per-gem override",
    config_override_copy: "A family max-level always takes priority over the global value.",
    config_resolver_title: "Mapped + fallback",
    config_resolver_copy: "Map templates explicitly or use convention fallback with GEMID_LEVEL.",
    copy: "Copy",
    copied: "Copied",
    mi_title: "Fits your existing MMOItems workflow.",
    mi_copy: "Each gem template only needs the correct GEM_ID_LEVEL. Damage, defense, socket color, and real stats stay owned by MMOItems.",
    mi_editor_title: "Enter the stat as family_level",
    mi_editor_copy: "Repeat for damage_2, damage_3, and every remaining level.",
    mi_notice_title: "GEM_ID_LEVEL only identifies the gem.",
    mi_notice_copy: "This stat does not add power directly to the target item. MMOItems still applies the new level gem through its native behavior.",
    faq_title: "Frequently asked questions.",
    faq_copy: "Cases worth checking before moving gem progression to your production server.",
    faq_1_q: "What if the next-level template is missing?",
    faq_1_a: "The plugin does not consume the cursor gem or change the item. Check config levels and the MMOItems item id.",
    faq_2_q: "Does the upgrade use success-rate?",
    faq_2_a: "No. An exact-pair upgrade succeeds when the next-level template exists and is valid.",
    faq_3_q: "Will the same id at a different level overwrite?",
    faq_3_a: "No. That case returns to MMOItems so it can use the next valid socket if available.",
    faq_4_q: "Do socket color and item restrictions still work?",
    faq_4_a: "Yes. Non-upgrade cases use MMOItems native flow; upgrade cases apply the new gem through MMOItems API.",
    search_empty: "No matching section found.",
    footer_copy: "Usage wiki for MMOItems gemstone progression.",
    back_top: "Back to top",
    alt_overview: "OmniGemLevel overview showing two level-one gems becoming one level-two gem",
    alt_mechanics: "Before and after diagram of a gem socket upgrading from level one to level two",
    alt_behavior: "Safety matrix for exact matches, different levels, different ids, and max level",
    alt_config: "OmniGemLevel configuration with a global max level and per-gem override",
    alt_mmoitems: "Three steps for adding GEM_ID_LEVEL, mapping templates, and upgrading by drag and drop"
  }
};

const langParam = new URLSearchParams(window.location.search).get("lang");
const storedLang = localStorage.getItem("omniGemLevelLangV3");
let currentLang = langParam === "en" || langParam === "vi"
  ? langParam
  : (storedLang === "en" || storedLang === "vi" ? storedLang : "vi");

function applyLanguage(lang) {
  currentLang = lang;
  localStorage.removeItem("omniGemLevelLang");
  localStorage.removeItem("omniGemLevelLangV2");
  localStorage.setItem("omniGemLevelLangV3", lang);
  document.documentElement.lang = lang;
  document.title = translations[lang].meta_title;

  const metaDescription = document.querySelector('meta[name="description"]');
  if (metaDescription) metaDescription.content = translations[lang].meta_description;

  document.querySelectorAll("[data-i18n]").forEach((node) => {
    const key = node.dataset.i18n;
    if (translations[lang][key]) node.textContent = translations[lang][key];
  });

  document.querySelectorAll("[data-i18n-placeholder]").forEach((node) => {
    const key = node.dataset.i18nPlaceholder;
    if (translations[lang][key]) node.placeholder = translations[lang][key];
  });

  document.querySelectorAll("[data-i18n-alt]").forEach((node) => {
    const key = node.dataset.i18nAlt;
    if (translations[lang][key]) node.alt = translations[lang][key];
  });

  document.getElementById("langToggle").textContent = lang === "vi" ? "EN" : "VI";
  filterSearch();
}

function filterSearch() {
  const input = document.getElementById("wikiSearch");
  const query = input.value.trim().toLocaleLowerCase(currentLang);
  const terms = query.split(/\s+/).filter(Boolean);
  const items = [...document.querySelectorAll(".search-item")];
  let visible = 0;

  items.forEach((item) => {
    const haystack = `${item.textContent} ${item.dataset.search || ""}`.toLocaleLowerCase(currentLang);
    const matches = terms.length === 0 || terms.every((term) => haystack.includes(term));
    item.hidden = !matches;
    if (matches) visible += 1;
  });

  document.getElementById("searchEmpty").hidden = visible !== 0;
}

async function copyText(text) {
  if (navigator.clipboard && window.isSecureContext) {
    await navigator.clipboard.writeText(text);
    return;
  }

  const area = document.createElement("textarea");
  area.value = text;
  area.setAttribute("readonly", "");
  area.style.position = "fixed";
  area.style.opacity = "0";
  document.body.appendChild(area);
  area.select();
  document.execCommand("copy");
  area.remove();
}

document.getElementById("langToggle").addEventListener("click", () => {
  applyLanguage(currentLang === "vi" ? "en" : "vi");
});

document.getElementById("wikiSearch").addEventListener("input", filterSearch);

document.querySelectorAll("[data-copy]").forEach((button) => {
  button.addEventListener("click", async () => {
    const source = document.getElementById(button.dataset.copy);
    if (!source) return;

    await copyText(source.textContent);
    button.textContent = translations[currentLang].copied;
    window.setTimeout(() => {
      button.textContent = translations[currentLang].copy;
    }, 1300);
  });
});

const navLinks = [...document.querySelectorAll(".nav a")];
const observedSections = navLinks
  .map((link) => document.querySelector(link.getAttribute("href")))
  .filter(Boolean);

if ("IntersectionObserver" in window) {
  const observer = new IntersectionObserver((entries) => {
    const visibleEntry = entries
      .filter((entry) => entry.isIntersecting)
      .sort((a, b) => b.intersectionRatio - a.intersectionRatio)[0];

    if (!visibleEntry) return;
    navLinks.forEach((link) => {
      const active = link.getAttribute("href") === `#${visibleEntry.target.id}`;
      link.classList.toggle("is-active", active);
      if (active) link.setAttribute("aria-current", "location");
      else link.removeAttribute("aria-current");
    });
  }, { rootMargin: "-30% 0px -60%", threshold: [0.05, 0.25, 0.5] });

  observedSections.forEach((section) => observer.observe(section));
}

applyLanguage(currentLang);
