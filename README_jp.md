# BareFlow — Minimal Core. Maximum Clarity.

[English](README.md) | [日本語](README_jp.md)

BareFlow は **仕様駆動・決定論的なフローエンジン**です。  
求められるのは **予測可能性・透明性・長期的な保守性**。

YAML に書いたとおりに **そのまま実行**されます。  
隠れたロジックなし。副作用なし。驚きなし。

コアは徹底的にミニマル。  
それ以外はすべて “エッジ” に追い出します。

---

# ✨ BareFlow が選ばれる理由

- **純粋な実行モデル**  
  暗黙の挙動ゼロ。逐次・決定論的な実行。

- **仕様駆動**  
  YAML → FlowDefinition → FlowEngine → 実行  
  という完全に透明なパイプライン。

- **透明性のための設計**  
  各ステップは StepTrace に完全記録され、監査にも強い。

- **ミニマルなコア、拡張可能なエッジ**  
  モジュール解決、YAML パース、統合処理は runtime 層へ。  
  コアを汚さない。

- **大規模チームでも予測可能**  
  DSL なし。マジックなし。隠れた状態なし。  
  FlowDefinition / FlowEngine / モジュールの明確な契約だけ。

---

# 🚀 BareFlow が「やること」と「やらないこと」

### BareFlow が *やること*:
- 純粋で逐次的なフロー実行  
- 仕様（Excel/YAML）と実行の橋渡し  
- Web/Batch ランタイムの基盤  
- アプリケーションの邪魔をしないフレームワーク

### BareFlow が *やらないこと*:
- ワークフローオーケストレーション  
- BPMN エンジン  
- ルールエンジン  
- 自動化ツール  
- ビジネスロジックの実装  

ビジネスロジックはあなたのモジュールに書くもの。  
BareFlow はただ “フローを実行する” だけ。

---

# 🧩 コアコンセプト

## **FlowDefinition（YAML → immutable モデル）**
宣言的でロジックを含まない構造体。  
以下を記述します：

- steps  
- input / output  
- retry policy  
- error handling  
- 任意の metadata  

FlowDefinition 自体は **一切のロジックを持ちません**。  
パース・バリデーションは runtime の責務。

---

## **ExecutionContext**
フロー全体を通して渡される **フラットな key-value ストア**。

- 階層なし  
- 式なし  
- マジックなし  
- `snapshot()` はトレース用  
- `view()` は現在値の参照用  

ExecutionContext は唯一の真実の源。

---

## **StepEvaluator**
BareFlow のプレースホルダモデル。

- `${name}` のみサポート  
- `${a.b}` のようなネストは非対応  
- input 評価: ExecutionContext から  
- output 評価: rawOutput → ExecutionContext  
- 解決できないプレースホルダは `null`  

シンプルで透明、そして決定論的。

---

## **StepInvoker**
実行境界。

- モジュールのメソッドを呼び出す  
- シグネチャは `Map<String,Object> → Map<String,Object>`  
- BusinessException はそのまま伝播  
- その他は SystemException に変換  

Invoker は **ビジネスロジックを持ちません**。

---

## **FlowEngine**
BareFlow の中心。

- ステップを逐次実行  
- retryPolicy（技術的リトライ）を適用  
- onError（ビジネス的エラー制御）を適用  
- input/output を評価  
- StepTrace を記録  

FlowEngine は純粋で副作用を持ちません。

---

## **StepTrace**
実行の完全な履歴（immutable）。

- beforeContext  
- evaluatedInput  
- rawOutput  
- error  
- timestamps  

デバッグ・監査・可観測性に最適。

---

## **FlowResult**
runtime が返す結果。

- 最終 ExecutionContext  
- 完全な StepTrace  

それ以上でも以下でもありません。

---

# 🏗 Runtime Layer

runtime 層は I/O と統合を担当します：

- YAML パース  
- モジュール解決  
- デフォルト StepInvoker  
- FlowExecutor  
- Web/Batch アダプタ  
- DI 統合  
- ロギング / メトリクス / トレース  

runtime は差し替え可能。  
core は永続的。

---

# 🧭 哲学

BareFlow は次の信念に基づいています：

> **フローエンジンは予測可能で、透明で、退屈であるべきだ。  
> 面白い部分はアプリケーション側にあるべきで、  
> フレームワーク側ではない。**

---

# 📄 ライセンス

BareFlow は **MIT License** のもとで公開されています。