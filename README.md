# BareFlow — Minimal Core. Maximum Clarity.

[English](README.md) | [日本語](README_jp.md)

BareFlow is a **specification‑driven, deterministic flow engine** designed for systems that demand  
**predictability, transparency, and long‑term maintainability**.

It executes flows **exactly as defined** in YAML.  
No hidden logic. No side effects. No surprises.

The core remains intentionally minimal.  
Everything else lives at the edges.

---

# ✨ Why BareFlow?

- **Pure execution model**  
  Sequential, deterministic, and free of implicit behavior.

- **Specification‑driven**  
  YAML → FlowDefinition → FlowEngine → Execution  
  A perfectly transparent pipeline.

- **Transparent by design**  
  Every step is recorded in a clean, immutable StepTrace.

- **Minimal core, extensible edges**  
  Runtime layers provide module resolution, YAML parsing, and integrations—  
  without polluting the engine.

- **Predictable for large teams**  
  No DSL. No magic. No hidden state.  
  Just a clean contract between FlowDefinition, FlowEngine, and your modules.

---

# 🚀 What BareFlow *is* (and *is not*)

### BareFlow *is*:
- A pure, sequential flow execution engine  
- A bridge between business specification (Excel/YAML) and execution  
- A predictable foundation for Web/Batch runtimes  
- A framework that stays out of your way

### BareFlow *is not*:
- A workflow orchestrator  
- A BPMN engine  
- A rules engine  
- An automation tool  
- A place to write business logic  

Your business logic belongs in your modules.  
BareFlow just runs the flow.

---

# 🧩 Core Concepts

## **FlowDefinition (YAML → immutable model)**
A declarative, logic‑free structure describing:
- steps  
- inputs / outputs  
- retry policy  
- error handling  
- optional metadata  

FlowDefinition contains **no behavior**.  
Validation and parsing belong to the runtime.

---

## **ExecutionContext**
A **flat key‑value store** passed through the flow.

- No hierarchy  
- No expressions  
- No magic  
- `snapshot()` for trace  
- `view()` for inspection  

The context is the single source of truth.

---

## **StepEvaluator**
BareFlow’s placeholder model:

- Supports only `${name}`  
- No nested expressions (`${a.b}` is not allowed)  
- Input evaluation: from ExecutionContext  
- Output evaluation: rawOutput → ExecutionContext  
- Unresolved placeholders become `null`

Simple. Transparent. Deterministic.

---

## **StepInvoker**
The execution boundary.

- Calls your module’s method  
- Signature: `Map<String,Object> → Map<String,Object>`  
- BusinessException is propagated  
- Other exceptions become SystemException  

Invoker contains **no business logic**.

---

## **FlowEngine**
The heart of BareFlow.

- Executes steps sequentially  
- Applies retry policy (system-level)  
- Applies onError rules (business-level)  
- Evaluates input/output  
- Records StepTrace  

FlowEngine is pure and side‑effect‑free.

---

## **StepTrace**
A complete, immutable record of execution:

- beforeContext  
- evaluatedInput  
- rawOutput  
- error  
- timestamps  

Perfect for debugging, auditing, and observability.

---

## **FlowResult**
Returned by the runtime:

- final ExecutionContext  
- full StepTrace  

Nothing more. Nothing less.

---

# 🏗 Runtime Layer

The runtime provides integrations and I/O:

- YAML parsing  
- Module resolution  
- Default StepInvoker  
- FlowExecutor  
- Web/Batch adapters  
- DI integration  
- Logging / metrics / tracing  

The runtime is replaceable.  
The core is eternal.

---

# 🧭 Philosophy

BareFlow is built on a simple belief:

> **A flow engine should be predictable, transparent, and boring.  
> The interesting parts belong in your application—not the framework.**

---

# 📄 License

BareFlow is released under the **MIT License**.