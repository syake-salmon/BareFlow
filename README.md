# BareFlow  

[English](README.md) | [日本語](README_jp.md)

### Minimal core. Maximum clarity.

BareFlow is a specification‑driven flow engine built for systems that demand predictability, transparency, and long‑term maintainability.  
It executes flows exactly as defined in YAML—no hidden logic, no side effects, no surprises.  
The core stays intentionally minimal, while optional extensions let you enrich behavior when your application needs it.

---

## ✨ Why BareFlow?

- **Pure execution model**  
  Sequential, deterministic flow execution with zero implicit behavior.

- **Specification‑driven**  
  What you write in YAML is exactly what runs. Nothing more, nothing less.

- **Transparent by design**  
  Every step is recorded in a clean, audit‑friendly StepTrace.

- **Minimal core, extensible edges**  
  Hooks, interceptors, and runtime integrations let you scale without polluting the engine.

- **Predictable for large teams**  
  No magic. No DSL complexity. No hidden state.  
  Just a clean contract between FlowDefinition, FlowEngine, and your modules.

---

## 🚀 What BareFlow *is* (and *is not*)

### BareFlow *is*:
- A pure, sequential flow execution engine  
- A bridge between specification (Excel/YAML) and execution  
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

## 🧩 Core Concepts

- **FlowDefinition (YAML)**  
  A declarative, logic‑free specification of steps, inputs, outputs, and error handling.

- **ExecutionContext**  
  A pure data container passed through the flow.

- **StepEvaluator**  
  Resolves `${key}` references from the context.

- **StepInvoker**  
  Calls your modules with strict Input/Output DTOs.

- **FlowResult**  
  Final status, merged context, and full StepTrace.

- **Runtime Layer**  
  Web/Batch integration, transactions, parallelism, backoff, and lifecycle management.

---

## 🧭 Philosophy

BareFlow is built on a simple belief:

> **A flow engine should be predictable, transparent, and boring.  
> The interesting parts belong in your application—not the framework.**

---

## 📄 License

BareFlow is released under the **MIT License**.