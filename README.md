# FlClash Smart Core

[FlClash](https://github.com/chen08209/FlClash)

[Mihomo Smart](https://github.com/vernesong/mihomo)

[Smart 策略使用方法](https://github.com/vernesong/OpenClash/releases/tag/mihomo)

## 注意事项

- 只有 Android ARMv8 版本，其他版本不会开发

- FlClash 代理不会显示 smart 分组

- 如果在 web 面板 smart 分组选择节点，需要重启内核才能取消固定。

## 使用场景

- 适合节点不稳的分组，如果节点平时比较稳定，不建议使用 smart

- smart 策略组会同时使用多个节点，失败立即切换，对于节点不稳的分组，经常超时，速度慢，可以改善使用体验，但是会经常跳 IP

- smart 分组可能会带来的影响。

  - 下载大文件时，可能会强制切换节点导致下载失败。

  - IP 跳动可能会频繁人机验证。

- 对于 IP 比较严格的场景，直接添加单独分组，使用 url-test 或者 selector

## 多订阅分组策略推荐

- Proxy (selector) Fallback, Auto_Group_1, Auto_Group_2, Auto_Group_3, Auto_Group_4 默认选择 Fallback
- Fallback (fallback) Auto_Primary, Auto_Backup 主用失败自动切换到备用
- Auto_Primary (smart) Auto_Group_1, Auto_Group_2 主用智选
- Auto_Backup (smart) Auto_Group_3, Auto_Group_4 备用智选
- Auto_Group_1 (smart) Node_1, Node_2 主用节点分组1，可以放周期性订阅
- Auto_Group_2 (smart) Node_1, Node_2 主用节点分组2
- Auto_Group_3 (smart) Node_1, Node_2 备用节点分组1，可以放不限时订阅
- Auto_Group_4 (smart) Node_1, Node_2 备用节点分组2

```
# 推荐使用 proxy-providers，直接填写订阅链接就能使用
# 只有基础配置分组，如果需要自行添加
proxy-providers:
  主用订阅名称:
    type: http
    url: 主用订阅链接
    interval: 3600
    path: ./proxies/主用订阅名称.yaml
    health-check:
      enable: true
      url: https://www.gstatic.com/generate_204
  备用订阅名称:
    type: http
    url: 备用订阅链接
    interval: 3600
    path: ./proxies/备用订阅名称.yaml
    health-check:
      enable: true
      url: https://www.gstatic.com/generate_204
rules:
  - RULE-SET,lancidr,DIRECT
  - RULE-SET,private,DIRECT
  - RULE-SET,reject,Reject
  - GEOSITE,category-ai-!cn,AI
  - GEOSITE,anthropic,AI
  - GEOSITE,microsoft,Microsoft
  - GEOSITE,netflix,Netflix
  - RULE-SET,google,Proxy
  - RULE-SET,telegramcidr,Proxy
  - RULE-SET,apple,DIRECT
  - RULE-SET,icloud,DIRECT
  - RULE-SET,proxy,Proxy
  - RULE-SET,direct,DIRECT
  - RULE-SET,cncidr,DIRECT
  - GEOIP,LAN,DIRECT
  - GEOIP,CN,DIRECT
  - MATCH,Final
rule-providers:
  reject:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/reject.txt
    type: http
    interval: 86400
    behavior: domain
    path: ./rules/reject.yaml
  icloud:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/icloud.txt
    type: http
    interval: 86400
    behavior: domain
    path: ./rules/icloud.yaml
  apple:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/apple.txt
    type: http
    interval: 86400
    behavior: domain
    path: ./rules/apple.yaml
  google:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/google.txt
    type: http
    interval: 86400
    behavior: domain
    path: ./rules/google.yaml
  proxy:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/proxy.txt
    type: http
    interval: 86400
    behavior: domain
    path: ./rules/proxy.yaml
  direct:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/direct.txt
    type: http
    interval: 86400
    behavior: domain
    path: ./rules/direct.yaml
  private:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/private.txt
    type: http
    interval: 86400
    behavior: domain
    path: ./rules/private.yaml
  telegramcidr:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/telegramcidr.txt
    type: http
    interval: 86400
    behavior: ipcidr
    path: ./rules/telegramcidr.yaml
  cncidr:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/cncidr.txt
    type: http
    interval: 86400
    behavior: ipcidr
    path: ./rules/cncidr.yaml
  lancidr:
    url: https://cdn.jsdelivr.net/gh/Loyalsoldier/clash-rules@release/lancidr.txt
    type: http
    interval: 86400
    behavior: ipcidr
    path: ./rules/lancidr.yaml
proxy-groups:
  - name: Proxy
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Static.png
    type: select
    proxies:
      - Fallback
      - Auto_主用订阅名称
      - Auto_备用订阅名称
  - name: Fallback
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Auto.png
    type: fallback
    proxies:
      - Auto_Primary
      - Auto_Backup
    url: https://www.gstatic.com/generate_204
  - name: Auto_Primary
    type: smart
    proxies:
      - Auto_主用订阅名称
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Auto.png
    uselightgbm: true
  - name: Auto_Backup
    type: smart
    proxies:
      - Auto_备用订阅名称
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Auto.png
    uselightgbm: true
  - name: Auto_主用订阅名称
    type: smart
    use:
      - 主用订阅名称
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Auto.png
    uselightgbm: true
  - name: Auto_备用订阅名称
    type: smart
    use:
      - 备用订阅名称
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Auto.png
    uselightgbm: true
  - name: AI
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/OpenAI.png
    type: select
    include-all: true
    proxies:
      - Proxy
  - name: Netflix
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Netflix.png
    type: select
    include-all: true
    proxies:
      - Proxy
  - name: Microsoft
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Microsoft.png
    type: select
    include-all: true
    proxies:
      - Proxy
      - DIRECT
  - name: Reject
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Adblock.png
    type: select
    proxies:
      - REJECT
      - DIRECT
  - name: Final
    icon: https://raw.githubusercontent.com/Orz-3/mini/master/Color/Final.png
    type: select
    proxies:
      - Proxy
      - DIRECT
```

## 查看节点和日志

使用 web 面板查看 smart 分组

需要用 [MetaCubeXD](https://metacubex.github.io/metacubexd) 查看

![代理](https://i.imgur.com/bfSsECL.png)

查看 smart 日志

![日志](https://i.imgur.com/b2IOe4f.png)
