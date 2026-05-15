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

- 对于节点不稳的分组，经常超时，速度慢，可以改善使用体验，但是会经常跳 IP

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

smart 策略组会同时使用多个节点，好处失败立即切换，坏处会跳 IP

## 查看节点和日志

使用 web 面板查看 smart 分组

需要用 [MetaCubeXD](https://metacubex.github.io/metacubexd) 查看

![代理](https://i.imgur.com/bfSsECL.png)

查看 smart 日志

![日志](https://i.imgur.com/b2IOe4f.png)
